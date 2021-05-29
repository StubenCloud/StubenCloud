package eu.pixelstube.cloud.packet;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 22.05.2021
 * Copyright© 2021 Max H.
 **/
public abstract class AbstractPacket {

    private JSONObject jsonObject;
    private final int id;
    private final String name;
    private final List<PacketType> packetType;

    public AbstractPacket(int id, String name, PacketType... packetType) {
        this.id = id;
        this.name = name;
        this.packetType = Arrays.asList(packetType);
    }

    public abstract void handle();

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONObject getJson(){
        return jsonObject;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PacketType> getPacketType() {
        return packetType;
    }
}
