package eu.pixelstube.cloud.type;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 21.05.2021
 * Copyright© 2021 Max H.
 **/
public enum GroupType {

    SERVER("Server"),
    LOBBY("Lobby"),
    PROXY("Proxy");

    String name;

    GroupType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isProxy(){
        return this == PROXY;
    }

    public boolean isServer(){
        return this == SERVER;
    }

    public boolean isLobby(){
        return this == LOBBY;
    }

}
