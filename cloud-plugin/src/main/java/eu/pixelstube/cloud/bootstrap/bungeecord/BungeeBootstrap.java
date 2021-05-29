package eu.pixelstube.cloud.bootstrap.bungeecord;

import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.bootstrap.bungeecord.commands.CloudCommand;
import eu.pixelstube.cloud.bootstrap.bungeecord.listener.BungeeListener;
import eu.pixelstube.cloud.bootstrap.bungeecord.reconnect.ReconnectHandlerImpl;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class BungeeBootstrap extends Plugin {

    private static BungeeBootstrap instance;

    @Override
    public void onLoad() {
        getProxy().setReconnectHandler(new ReconnectHandlerImpl());
    }

    @Override
    public void onEnable() {
        instance = this;

        new CloudPlugin();

        getProxy().getPluginManager().registerListener(this, new BungeeListener());
        getProxy().getPluginManager().registerCommand(this, new CloudCommand());

        ProxyServer.getInstance().getConfigurationAdapter().getServers().clear();
        ProxyServer.getInstance().getServers().clear();

        for(ListenerInfo listenerInfo : ProxyServer.getInstance().getConfigurationAdapter().getListeners()){
            listenerInfo.getServerPriority().clear();
        }

        registerFallback();

    }

    public void sendTablist(UUID uuid, String var1, String var2){

        getProxy().getPlayer(uuid).setTabHeader(new TextComponent(var1), new TextComponent(var2));

    }

    public void registerFallback(){
        registerService("fallback", new InetSocketAddress("127.0.0.1", 0));
    }

    public void registerService(String name, InetSocketAddress inetSocketAddress){
        if (!ProxyServer.getInstance().getServers().containsKey(name)) {
            ServerInfo serverInfo = ProxyServer.getInstance().constructServerInfo(name, inetSocketAddress, "A stubencloud service", false);
            ProxyServer.getInstance().getServers().put(name, serverInfo);
        }
    }

    public static BungeeBootstrap getInstance() {
        return instance;
    }
}
