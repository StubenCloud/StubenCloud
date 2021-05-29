package eu.pixelstube.cloud.backend.external.handler;

import eu.pixelstube.cloud.event.IEvent;
import eu.pixelstube.cloud.event.IEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class EventHandler implements IEventHandler {

    private final List<IEvent> events;

    public EventHandler() {
        this.events = new ArrayList<>();
    }

    @Override
    public void registerEvent(IEvent event) {
        this.events.add(event);
    }

    @Override
    public void call(IEvent event) {
        Class<? extends IEvent> eventClazz = event.getClass();

    }

    @Override
    public List<IEvent> getEvents() {
        return this.events;
    }
}
