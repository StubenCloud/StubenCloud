package eu.pixelstube.cloud.connection.manager;

import eu.pixelstube.cloud.packet.AbstractPacket;
import eu.pixelstube.cloud.packet.IAbstractPacketManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 22.05.2021
 * Copyright© 2021 Max H.
 **/
public class AbstractPacketManager implements IAbstractPacketManager {

    private final List<AbstractPacket> packets;

    public AbstractPacketManager() {
        this.packets = new ArrayList<>();
    }

    @Override
    public void registerPacket(AbstractPacket abstractPacket) {
        packets.add(abstractPacket);
    }

    @Override
    public List<AbstractPacket> getPackets() {
        return packets;
    }
}
