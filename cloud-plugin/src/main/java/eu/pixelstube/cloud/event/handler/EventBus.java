package eu.pixelstube.cloud.event.handler;

import eu.pixelstube.cloud.event.EventInject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class EventBus {

    private final Map<Class<?>, Map<Byte, Map<Object, Method[]>>> byListenerAndPriority = new HashMap<>();
    private final Map<Class<?>, EventHandlerMethod[]> byEventBaked = new ConcurrentHashMap<>();
    private final Lock lock = new ReentrantLock();

    public void post(Object event){
        EventHandlerMethod[] handlers = byEventBaked.get(event.getClass());

        if(handlers != null){
            for(EventHandlerMethod method : handlers){
                try {
                    method.invoke(event);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Map<Class<?>, Map<Byte, Set<Method>>> findHandlers(Object listener){
        Map<Class<?>, Map<Byte, Set<Method>>> handler = new HashMap<>();

        for(Method method : listener.getClass().getDeclaredMethods()){

            EventInject eventInject = method.getAnnotation(EventInject.class);
            if(eventInject != null){
                Class<?>[] params = method.getParameterTypes();
                if(params.length != 1){
                    continue;
                }
                Map<Byte, Set<Method>> prioritiesMap = handler.computeIfAbsent(params[0], k -> new HashMap<>());
                Set<Method> priority = prioritiesMap.computeIfAbsent(eventInject.priority(), k -> new HashSet<>());

                priority.add(method);

            }

        }

        return handler;
    }

    public void register(Object listener){
        Map<Class<?>, Map<Byte, Set<Method>>> handler = findHandlers(listener);
        lock.lock();

        try {
            for(Map.Entry<Class<?>, Map<Byte, Set<Method>>> entry : handler.entrySet()){
                Map<Byte, Map<Object, Method[]>> prioritiesMap = byListenerAndPriority.computeIfAbsent(entry.getKey(), k -> new HashMap<>());

                for (Map.Entry<Byte, Set<Method>> byteSetEntry : entry.getValue().entrySet()){
                    Map<Object, Method[]> currentPriorityMap = prioritiesMap.computeIfAbsent(byteSetEntry.getKey(), k -> new HashMap<>());
                    Method[] baked = new Method[byteSetEntry.getValue().size()];
                    currentPriorityMap.put(listener, byteSetEntry.getValue().toArray(baked));
                }

                bakeHandlers(entry.getKey());
            }
        }finally {
            lock.unlock();
        }

    }

    private void bakeHandlers(Class<?> eventClass){
        Map<Byte, Map<Object, Method[]>> handlersByPriority = byListenerAndPriority.get(eventClass);
        if(handlersByPriority != null){

            List<EventHandlerMethod> handlerMethods = new ArrayList<>(handlersByPriority.size() * 2);
            byte value = Byte.MIN_VALUE;
            do {

                Map<Object, Method[]> handlerByListener = handlersByPriority.get(value);
                if(handlerByListener != null){
                    for(Map.Entry<Object, Method[]> listenerHandlers : handlerByListener.entrySet()){
                        for(Method method : listenerHandlers.getValue()){
                            EventHandlerMethod eventHandlerMethod = new EventHandlerMethod(listenerHandlers.getKey(), method);
                            handlerMethods.add(eventHandlerMethod);
                        }
                    }
                }

            } while (value++ < Byte.MAX_VALUE);
            byEventBaked.put(eventClass, handlerMethods.toArray(new EventHandlerMethod[handlerMethods.size()]));
        } else {
            byEventBaked.remove(eventClass);
        }
    }

}
