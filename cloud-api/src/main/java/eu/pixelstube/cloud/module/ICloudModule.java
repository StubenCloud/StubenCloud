package eu.pixelstube.cloud.module;

import eu.pixelstube.cloud.logger.ICloudLogger;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ICloudModule {

    void onInitialization(ICloudLogger cloudLogger);

    void onReload();

}
