package eu.pixelstube.cloud.bootstrap.spigot.events;

import eu.pixelstube.cloud.service.ICloudService;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
public class SpigotCloudServiceUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    private final ICloudService cloudService;

    public SpigotCloudServiceUpdateEvent(ICloudService cloudService) {
        this.cloudService = cloudService;
    }

    public ICloudService getCloudService() {
        return cloudService;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
