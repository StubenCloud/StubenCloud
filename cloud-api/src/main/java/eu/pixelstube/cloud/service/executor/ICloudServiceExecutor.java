package eu.pixelstube.cloud.service.executor;

import eu.pixelstube.cloud.service.ICloudService;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 22.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ICloudServiceExecutor {

    /**
     * Start's the service
     */
    void start();

    /**
     * Stops the service
     */
    void stop();

    /**
     * Shutdowns the service
     */
    void shutdown();

    /**
     * Returns the cloud service process
     */
    Process getProcess();

    /**
     * Executes command on a service
     * @param command
     */
    void executeCommand(String command);

    /**
     * Returns the cloud service from the executor
     */
    ICloudService getCloudService();

}
