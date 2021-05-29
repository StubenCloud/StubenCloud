package eu.pixelstube.cloud.defaults;

import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.bootstrap.bungeecord.BungeeBootstrap;
import eu.pixelstube.cloud.bootstrap.velocity.VelocityBootstrap;
import eu.pixelstube.cloud.player.ICloudPlayer;
import eu.pixelstube.cloud.player.manager.ICloudPlayerManager;
import eu.pixelstube.cloud.service.ICloudService;
import eu.pixelstube.cloud.type.GroupVersion;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.json.JSONObject;

import java.util.*;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class DefaultCloudPlayerManger implements ICloudPlayerManager {

    private static final List<ICloudPlayer> cloudPlayers = new ArrayList<>();

    @Override
    public ICloudPlayer getCachedCloudPlayer(UUID uuid) {

        ICloudPlayer cloudPlayer = cloudPlayers.stream().filter(iCloudPlayer -> iCloudPlayer.getUniqueId().equals(uuid)).findAny().orElse(null);

        if(cloudPlayer == null){
            cloudPlayers.add(new ICloudPlayer() {
                @Override
                public void connect(ICloudService var1) {
                    if (CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.BUNGEECORD) || CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.WATERFALL)) {

                        BungeeBootstrap.getInstance().getProxy().getPlayer(uuid).connect(BungeeBootstrap.getInstance().getProxy().getServerInfo(var1.getServiceIdName()));

                    } else if(CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.VELOCITY)){

                        Objects.requireNonNull(VelocityBootstrap.getInstance().getServer().getPlayer(uuid).orElse(null)).createConnectionRequest(VelocityBootstrap.getInstance().getServer().getServer(var1.getServiceIdName()).orElse(null));

                    }
                }

                @Override
                public void sendActionbar(String var1) {
                    if (CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.BUNGEECORD) || CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.WATERFALL)) {

                        BungeeBootstrap.getInstance().getProxy().getPlayer(uuid).sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(var1));

                    }
                }

                @Override
                public void kick() {
                    if (CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.BUNGEECORD) || CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.WATERFALL)) {

                        BungeeBootstrap.getInstance().getProxy().getPlayer(uuid).disconnect(new TextComponent("§cYou were kicked from the server!"));

                    }
                }

                @Override
                public void kick(String var1) {
                    if (CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.BUNGEECORD) || CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.WATERFALL)) {

                        BungeeBootstrap.getInstance().getProxy().getPlayer(uuid).disconnect(new TextComponent(var1));

                    }
                }

                @Override
                public void sendTablist(String var1, String var2) {
                    if (CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.BUNGEECORD) || CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.WATERFALL)) {

                        BungeeBootstrap.getInstance().sendTablist(getUniqueId(), var1, var2);

                    }
                }

                @Override
                public void setProperty(String var1, Object var2) {

                }

                @Override
                public ICloudService getConnectedProxy() {
                    return null;
                }

                @Override
                public String getConnectedProxyName() {
                    return null;
                }

                @Override
                public ICloudService getConnectedService() {
                    return null;
                }

                @Override
                public String getConnectedServiceName() {
                    return null;
                }

                @Override
                public JSONObject getProperties() {
                    return null;
                }

                @Override
                public Map<String, Object> getPropertiesMap() {
                    return null;
                }

                @Override
                public Object getProperty() {
                    return null;
                }

                @Override
                public UUID getUniqueId() {
                    return uuid;
                }
            });
        }

        return cloudPlayers.stream().filter(iCloudPlayer -> iCloudPlayer.getUniqueId().equals(uuid)).findAny().orElse(null);
    }

    @Override
    public List<ICloudPlayer> getCloudPlayers() {
        return cloudPlayers;
    }
}
