package eu.pixelstube.cloud.event.events;

import eu.pixelstube.cloud.event.IEvent;
import eu.pixelstube.cloud.service.ICloudService;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudServiceUpdateEvent extends IEvent {

    private final ICloudService cloudService;

    public CloudServiceUpdateEvent(ICloudService cloudService) {
        this.cloudService = cloudService;
    }

    public ICloudService getCloudService() {
        return cloudService;
    }
}
