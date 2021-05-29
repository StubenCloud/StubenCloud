package eu.pixelstube.cloud.packet;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 22.05.2021
 * Copyright© 2021 Max H.
 **/
public interface IAbstractPacketManager {

    /**
     * Register a packet
     * @param abstractPacket
     */
    void registerPacket(AbstractPacket abstractPacket);

    /**
     * Returns all registered packets
     * @return
     */
    List<AbstractPacket> getPackets();

}
