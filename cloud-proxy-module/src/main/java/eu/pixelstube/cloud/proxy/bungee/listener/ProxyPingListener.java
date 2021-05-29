package eu.pixelstube.cloud.proxy.bungee.listener;

import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.proxy.bungee.BungeeBootstrap;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class ProxyPingListener implements Listener {

    @EventHandler
    public void handle(ProxyPingEvent event){

        ICloudGroup cloudGroup = CloudPlugin.getInstance().thisService().getCloudGroup();

        String maintenance = String.join("\n", BungeeBootstrap.getInstance().getMotdConfiguration().getMaintenance());
        String online = String.join("\n", BungeeBootstrap.getInstance().getMotdConfiguration().getOnline());

        ServerPing serverPing = event.getResponse();

        if(cloudGroup.isMaintenance()){
            serverPing.setDescriptionComponent(new TextComponent(maintenance));

            serverPing.setVersion(new ServerPing.Protocol(BungeeBootstrap.getInstance().getMessageConfiguration().getMaintenance(), -1));
            return;

        }

        serverPing.setDescriptionComponent(new TextComponent(online));

    }

}
