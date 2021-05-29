package eu.pixelstube.cloud.service;

import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.player.ICloudPlayer;
import eu.pixelstube.cloud.service.executor.ICloudServiceExecutor;
import eu.pixelstube.cloud.service.status.CloudServiceStatus;
import eu.pixelstube.cloud.type.GroupVersion;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 18.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ICloudService {

    /**
     * Returns the group name from the service
     */
    String getGroupName();

    /**
     * Returns the service id eg. Lobby-1 then the id will be 1
     */
    int getServiceId();

    /**
     * Returns the uniqueId from the service
     */
    UUID getUniqueId();

    /**
     * Returns the service name
     */
    String getName();

    /**
     * Returns the service name + the service id. e.g. Lobby-1
     */
    String getServiceIdName();

    /**
     * Returns a list with all players whp online on this service
     */
    List<ICloudPlayer> getCurrentPlayers();

    /**
     * Returns the cloud group
     */
    ICloudGroup getCloudGroup();

    /**
     * Returns the cloud service status
     */
    CloudServiceStatus getServiceStatus();

    /**
     * Returns the cloud service executor
     */
    ICloudServiceExecutor getServiceExecutor();

    /**
     * Returns all messages of the console
     */
    CopyOnWriteArrayList<String> getConsoleMessages();

    /**
     * Returns the group version form the service. e.g Paperspigot-1.8.8
     */
    GroupVersion getVersion();

    /**
     * Returns the port from the service
     */
    int getPort();

    /**
     * Set's the service state
     */
    void setStatus(CloudServiceStatus cloudServiceStatus);

    /**
     * Returns if the service is static
     */
    boolean isStatic();

    /**
     * Starts the service
     */
    void start();

    /**
     * Updates the service to the network
     */
    void update();

}
