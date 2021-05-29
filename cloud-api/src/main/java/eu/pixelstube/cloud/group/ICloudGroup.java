package eu.pixelstube.cloud.group;

import eu.pixelstube.cloud.template.ITemplate;
import eu.pixelstube.cloud.type.GroupType;
import eu.pixelstube.cloud.type.GroupVersion;

import java.util.UUID;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 21.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ICloudGroup {

    /**
     * Returns the uuid of the group
     */
    UUID getUUID();

    /**
     * Returns the name of the group. e.g. Proxy
     */
    String getName();

    /**
     * Returns the max service count
     */
    int getMaxServices();

    /**
     * Returns the min service count
     */
    int getMinServices();

    /**
     * Returns the max memory of the group
     */
    int getMaxMemory();

    /**
     * Returns the percentage to start a new service
     */
    int getPercentageToStartNewService();

    /**
     * Returns the group version. e.g. PaperSpigot-1.8.8
     */
    GroupVersion getGroupVersion();

    /**
     * Returns the group type. e.g. SERVER
     */
    GroupType getGroupType();

    /**
     * Returns if maintenance is on
     */
    boolean isMaintenance();

    /**
     * Returns the max players
     */
    int getMaxPlayers();

    /**
     * Returns the group template
     */
    ITemplate getTemplate();

}
