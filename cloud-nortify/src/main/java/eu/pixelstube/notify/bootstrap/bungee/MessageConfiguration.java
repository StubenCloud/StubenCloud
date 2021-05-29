package eu.pixelstube.notify.bootstrap.bungee;

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
