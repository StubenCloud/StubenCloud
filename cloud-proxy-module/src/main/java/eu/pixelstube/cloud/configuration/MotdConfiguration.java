package eu.pixelstube.cloud.configuration;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class MotdConfiguration {

    private final List<String> maintenance;
    private final List<String> online;

    public MotdConfiguration(List<String> maintenance, List<String> online) {
        this.maintenance = maintenance;
        this.online = online;
    }

    public List<String> getMaintenance() {
        return maintenance;
    }

    public List<String> getOnline() {
        return online;
    }
}
