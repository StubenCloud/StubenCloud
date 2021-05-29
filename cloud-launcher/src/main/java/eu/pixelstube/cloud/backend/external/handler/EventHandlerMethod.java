package eu.pixelstube.cloud.backend.external.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class EventHandlerMethod {

    private final Object listener;
    private final Method method;

    public EventHandlerMethod(Object listener, Method method) {
        this.listener = listener;
        this.method = method;
    }

    public void invoke(Object event) throws InvocationTargetException, IllegalAccessException {
        method.invoke(listener, event);
    }

}
