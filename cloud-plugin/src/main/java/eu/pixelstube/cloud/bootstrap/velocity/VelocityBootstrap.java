package eu.pixelstube.cloud.bootstrap.velocity;


import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.bootstrap.velocity.listener.VelocityListener;
import org.slf4j.Logger;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 26.05.2021
 * Copyright© 2021 Max H.
 **/
@Plugin(id = "cloudplugin", name = "CloudPlugin", version = "1.0.0", authors = {"Haizoooon"})
public class VelocityBootstrap {

    private static VelocityBootstrap instance;

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public VelocityBootstrap(ProxyServer server, Logger logger){
        instance = this;

        this.server = server;
        this.logger = logger;

        new CloudPlugin();

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event){
        server.getEventManager().register(this, new VelocityListener());
    }

    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }

    public static VelocityBootstrap getInstance() {
        return instance;
    }
}
