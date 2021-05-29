package eu.pixelstube.cloud.configuration;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
public class WhitelistConfiguration {

    private final List<String> whitelistedPlayers;

    public WhitelistConfiguration(List<String> whitelistedPlayers) {
        this.whitelistedPlayers = whitelistedPlayers;
    }

    public List<String> getWhitelistedPlayers() {
        return whitelistedPlayers;
    }
}
