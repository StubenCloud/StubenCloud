package eu.pixelstube.cloud;

import eu.pixelstube.cloud.defaults.DefaultCloudGroupManager;
import eu.pixelstube.cloud.defaults.DefaultCloudPlayerManger;
import eu.pixelstube.cloud.defaults.DefaultCloudServiceManager;
import eu.pixelstube.cloud.group.manager.ICloudGroupManager;
import eu.pixelstube.cloud.player.manager.ICloudPlayerManager;
import eu.pixelstube.cloud.service.manager.ICloudServiceManager;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudAPI implements ICloudAPI {

    private static final CloudAPI instance = new CloudAPI();
    private final ICloudServiceManager cloudServiceManager = new DefaultCloudServiceManager();
    private final ICloudGroupManager cloudGroupManager = new DefaultCloudGroupManager();
    private final ICloudPlayerManager cloudPlayerManager = new DefaultCloudPlayerManger();

    @Override
    public ICloudPlayerManager getCloudPlayerManager() {
        return cloudPlayerManager;
    }

    @Override
    public ICloudServiceManager getCloudServiceManager() {
        return cloudServiceManager;
    }

    @Override
    public ICloudGroupManager getCloudGroupManager() {
        return cloudGroupManager;
    }

    public static CloudAPI getInstance() {
        return instance;
    }
}
