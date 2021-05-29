package eu.pixelstube.cloud.player.manager;

import eu.pixelstube.cloud.player.ICloudPlayer;

import java.util.List;
import java.util.UUID;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 22.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ICloudPlayerManager {

    /**
     * Returns a cloud player from uniqueId
     * @param uuid
     * @return
     */
    ICloudPlayer getCachedCloudPlayer(UUID uuid);

    /**
     * Returns all cached cloud players
     * @return
     */
    List<ICloudPlayer> getCloudPlayers();

}
