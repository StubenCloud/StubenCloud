package eu.pixelstube.cloud.configuration;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
public class MessageConfiguration {

    private final String prefix, maintenance;

    public MessageConfiguration(String prefix, String maintenance) {
        this.prefix = prefix;
        this.maintenance = maintenance;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getMaintenance() {
        return maintenance;
    }
}
