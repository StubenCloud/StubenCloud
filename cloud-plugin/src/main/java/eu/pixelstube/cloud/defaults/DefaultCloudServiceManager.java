package eu.pixelstube.cloud.defaults;

import eu.pixelstube.cloud.service.ICloudService;
import eu.pixelstube.cloud.service.manager.ICloudServiceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class DefaultCloudServiceManager implements ICloudServiceManager {

    private static final List<ICloudService> cloudServices = new ArrayList<>();

    @Override
    public ICloudService getCachedCloudService(String name) {
        return cloudServices.stream().filter(cloudService -> cloudService.getServiceIdName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    @Override
    public List<ICloudService> getCloudServices() {
        return cloudServices;
    }
}
