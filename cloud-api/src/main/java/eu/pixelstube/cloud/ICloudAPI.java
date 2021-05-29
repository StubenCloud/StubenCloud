package eu.pixelstube.cloud;

import eu.pixelstube.cloud.group.manager.ICloudGroupManager;
import eu.pixelstube.cloud.player.manager.ICloudPlayerManager;
import eu.pixelstube.cloud.service.manager.ICloudServiceManager;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 15.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ICloudAPI {

    ICloudPlayerManager getCloudPlayerManager();

    ICloudServiceManager getCloudServiceManager();

    ICloudGroupManager getCloudGroupManager();

}
