package eu.pixelstube.cloud.service.configuration;

import eu.pixelstube.cloud.service.ICloudService;

import java.io.File;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public interface IServiceConfiguration {

    /**
     * Configure the cloud service files
     * @param cloudService
     * @param file
     */
    void configure(ICloudService cloudService, File file);

}
