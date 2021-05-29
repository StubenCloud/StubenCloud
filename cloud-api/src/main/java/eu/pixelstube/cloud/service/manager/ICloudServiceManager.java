package eu.pixelstube.cloud.service.manager;

import eu.pixelstube.cloud.service.ICloudService;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ICloudServiceManager {

    ICloudService getCachedCloudService(String name);

    List<ICloudService> getCloudServices();

}
