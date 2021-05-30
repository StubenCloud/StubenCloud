package eu.pixelstube.notify.listener;

import eu.pixelstube.cloud.bootstrap.bungeecord.events.BungeeCloudServiceStartEvent;
import eu.pixelstube.cloud.bootstrap.bungeecord.events.BungeeCloudServiceStopEvent;
import eu.pixelstube.notify.bootstrap.bungee.BungeeModule;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/*
    » This File was created by ZxroGame
    » Copyright© 2021 Clemens R.
    » Date: 29.05.2021
 */

public class CloudListener implements Listener {

    @EventHandler
    public void handleBungeeCloudServiceStart(BungeeCloudServiceStartEvent event) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission("cloud.notify.message")) {
                player.sendMessage(BungeeModule.getInstance().getMessageConfiguration().getPrefix()
                        + BungeeModule.getInstance().getMessageConfiguration()
                        .getServiceStarted().replace("%SERVICE_NAME%", event.getCloudService().getName()));


            }
        }
    }

    @EventHandler
    public void handleBungeeCloudServiceStopEvent(BungeeCloudServiceStopEvent event) {

        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (player.hasPermission("cloud.notify.message")) {
                player.sendMessage(BungeeModule.getInstance().getMessageConfiguration().getPrefix()
                        + BungeeModule.getInstance().getMessageConfiguration()
                        .getServiceStopped().replace("%SERVICE_NAME%", event.getCloudService().getName()));


            }
        }

    }
}