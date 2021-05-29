package eu.pixelstube.cloud.bootstrap.bungeecord.listener;

import eu.pixelstube.cloud.CloudAPI;
import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.player.ICloudPlayer;
import eu.pixelstube.cloud.service.ICloudService;
import eu.pixelstube.cloud.type.GroupType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class BungeeListener implements Listener {

    @EventHandler
    public void handle(PostLoginEvent event) {
        event.getPlayer().setReconnectServer(null);

        ICloudService lobbyService = CloudAPI.getInstance().getCloudServiceManager().getCloudServices().stream().filter(iCloudService -> iCloudService.getCloudGroup().getGroupType().equals(GroupType.LOBBY)).findFirst().orElse(null);

        if (lobbyService == null) {
            return;
        }

        if (ProxyServer.getInstance().getServerInfo(lobbyService.getServiceIdName()) == null) {
            event.getPlayer().disconnect(new TextComponent("§cCloud service Lobby cannot be found!"));
        }

    }

    @EventHandler
    public void handle(ServerDisconnectEvent event) {
        ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(event.getPlayer().getUniqueId());

        CloudPlugin.getInstance().thisService().getCurrentPlayers().remove(cloudPlayer);
    }

    @EventHandler
    public void handle(ServerConnectEvent event) {
        ICloudService lobbyService = CloudAPI.getInstance().getCloudServiceManager().getCloudServices().stream().filter(iCloudService -> iCloudService.getCloudGroup().getGroupType().equals(GroupType.LOBBY)).findFirst().orElse(null);

        if (lobbyService == null) {
            return;
        }
        if (event.getTarget().getName().equalsIgnoreCase("fallback")) {
            ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo(lobbyService.getServiceIdName());
            event.setTarget(serverInfo);
        }

        ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(event.getPlayer().getUniqueId());
        CloudPlugin.getInstance().thisService().getCurrentPlayers().add(cloudPlayer);

        CloudPlugin.getInstance().getConnection().getClient().sendTCP(JsonLib.empty()
                .append("type", "player_connected")
                .append("uuid", cloudPlayer.getUniqueId())
                .append("name", event.getPlayer().getName())
                .append("address", event.getPlayer().getAddress().getAddress().getHostAddress()).getAsJsonString());

    }

}
