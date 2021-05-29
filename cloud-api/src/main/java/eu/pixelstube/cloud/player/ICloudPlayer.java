package eu.pixelstube.cloud.player;

import eu.pixelstube.cloud.service.ICloudService;
import org.json.JSONObject;

import java.util.Map;
import java.util.UUID;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 18.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ICloudPlayer {

    /**
     * Connect to player to another service
     * @param var1
     */
    void connect(ICloudService var1);

    /**
     * Sends the player a action bar
     * @param var1
     */
    void sendActionbar(String var1);

    void kick();

    void kick(String var1);

    void sendTablist(String var1, String var2);

    void setProperty(String var1, Object var2);

    ICloudService getConnectedProxy();

    String getConnectedProxyName();

    ICloudService getConnectedService();

    String getConnectedServiceName();

    JSONObject getProperties();

    Map<String, Object> getPropertiesMap();

    Object getProperty();

    UUID getUniqueId();

}
