package eu.pixelstube.notify.bootstrap.bungee;

/*
    » This File was created by ZxroGame
    » Copyright© 2021 Clemens R.
    » Date: 29.05.2021
 */

public class MessageConfiguration {

    private final String prefix, serviceStarted, serviceStopped;

    public MessageConfiguration(String prefix, String serviceStarted, String serviceStopped) {
        this.prefix = prefix;
        this.serviceStarted = serviceStarted;
        this.serviceStopped = serviceStopped;

    }

    public String getPrefix() {
        return prefix;
    }

    public String getServiceStarted() {
        return serviceStarted;
    }

    public String getServiceStopped() {
        return serviceStopped;
    }

}
