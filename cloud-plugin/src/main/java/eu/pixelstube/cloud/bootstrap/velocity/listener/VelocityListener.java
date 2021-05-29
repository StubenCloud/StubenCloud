package eu.pixelstube.cloud.bootstrap.velocity.listener;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import eu.pixelstube.cloud.CloudAPI;
import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.bootstrap.velocity.VelocityBootstrap;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.player.ICloudPlayer;
import eu.pixelstube.cloud.service.ICloudService;
import eu.pixelstube.cloud.type.GroupType;
import net.kyori.adventure.text.Component;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 26.05.2021
 * Copyright© 2021 Max H.
 **/
public class VelocityListener {

    @Subscribe(order = PostOrder.FIRST)
    public void handle(PostLoginEvent event){

        ICloudService lobbyService = CloudAPI.getInstance().getCloudServiceManager().getCloudServices().stream().filter(iCloudService -> iCloudService.getCloudGroup().getGroupType().equals(GroupType.LOBBY)).findFirst().orElse(null);

        if (lobbyService == null) {
            return;
        }

        if(!VelocityBootstrap.getInstance().getServer().getServer(lobbyService.getServiceIdName()).isPresent()){
            event.getPlayer().disconnect(Component.text("§cCloud service Lobby cannot be found"));
        }

    }

    @Subscribe
    public void handle(DisconnectEvent event){
        ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(event.getPlayer().getUniqueId());

        CloudPlugin.getInstance().thisService().getCurrentPlayers().remove(cloudPlayer);
    }

    @Subscribe
    public void handle(ServerPreConnectEvent event){

        if(!event.getResult().isAllowed()) return;

        Player player = event.getPlayer();

        ICloudService lobbyService = CloudAPI.getInstance().getCloudServiceManager().getCloudServices().stream().filter(iCloudService -> iCloudService.getCloudGroup().getGroupType().equals(GroupType.LOBBY)).findFirst().orElse(null);

        if (lobbyService == null) {
            return;
        }

        RegisteredServer serverInfo;

        if(event.getOriginalServer().getServerInfo().getName().equals("fallback")){
            serverInfo = VelocityBootstrap.getInstance().getServer().getServer(lobbyService.getServiceIdName()).orElse(null);
        } else {
            serverInfo = event.getOriginalServer();
        }

        if(serverInfo == null){
            player.disconnect(Component.text("§cNo fallback server was found..."));
            return;
        }

        ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(event.getPlayer().getUniqueId());

        cloudPlayer.connect(CloudAPI.getInstance().getCloudServiceManager().getCachedCloudService(serverInfo.getServerInfo().getName()));

        CloudPlugin.getInstance().thisService().getCurrentPlayers().add(cloudPlayer);

        CloudPlugin.getInstance().getConnection().getClient().sendTCP(JsonLib.empty()
                .append("type", "player_connected")
                .append("uuid", cloudPlayer.getUniqueId())
                .append("name", event.getPlayer().getUsername())
                .append("address", event.getPlayer().getRemoteAddress().getAddress().getHostAddress()).getAsJsonString());
    }

}
