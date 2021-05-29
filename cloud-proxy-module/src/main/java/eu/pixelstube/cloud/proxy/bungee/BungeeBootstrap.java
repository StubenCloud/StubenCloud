package eu.pixelstube.cloud.proxy.bungee;

import eu.pixelstube.cloud.CloudAPI;
import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.configuration.MessageConfiguration;
import eu.pixelstube.cloud.configuration.MotdConfiguration;
import eu.pixelstube.cloud.configuration.TablistConfiguration;
import eu.pixelstube.cloud.configuration.WhitelistConfiguration;
import eu.pixelstube.cloud.connection.ConnectionListener;
import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.proxy.bungee.listener.ProxyPingListener;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.concurrent.TimeUnit;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class BungeeBootstrap extends Plugin {

    private static BungeeBootstrap instance;
    private TablistConfiguration tablistConfiguration;
    private MotdConfiguration motdConfiguration;
    private MessageConfiguration messageConfiguration;
    private WhitelistConfiguration whitelistConfiguration;

    @Override
    public void onEnable() {
        instance = this;

        CloudPlugin.getInstance().getConnection().getClient().addListener(new ConnectionListener());

        getProxy().getPluginManager().registerListener(this, new ProxyPingListener());

    }

    public void start(){
        getProxy().getScheduler().schedule(this, () -> {

            String header = String.join("\n", getTablistConfiguration().getHeader());
            String footer = String.join("\n", getTablistConfiguration().getFooter());

            getProxy().getPlayers().forEach(proxiedPlayer -> proxiedPlayer.setTabHeader(new TextComponent(replace(proxiedPlayer, header)), new TextComponent(replace(proxiedPlayer, footer))));

        }, 500, 500, TimeUnit.MILLISECONDS);
    }

    private String replace(ProxiedPlayer proxiedPlayer, String line){

        ICloudGroup cloudGroup = CloudAPI.getInstance().getCloudGroupManager().getCloudGroups().stream().filter(iCloudGroup -> iCloudGroup.getName().equalsIgnoreCase(CloudPlugin.getInstance().thisService().getGroupName())).findAny().orElse(null);

        assert cloudGroup != null;

        return line.replace("%service_name%", proxiedPlayer.getServer().getInfo().getName()).replace("%max_players%", String.valueOf(CloudPlugin.getInstance().thisService().getCloudGroup().getMaxPlayers())).replace("%players%", String.valueOf(getProxy().getPlayers().size()));

    }

    public static BungeeBootstrap getInstance() {
        return instance;
    }

    public void setTablistConfiguration(TablistConfiguration tablistConfiguration) {
        this.tablistConfiguration = tablistConfiguration;
    }

    public MotdConfiguration getMotdConfiguration() {
        return motdConfiguration;
    }

    public MessageConfiguration getMessageConfiguration() {
        return messageConfiguration;
    }

    public void setMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
    }

    public void setMotdConfiguration(MotdConfiguration motdConfiguration) {
        this.motdConfiguration = motdConfiguration;
    }

    public WhitelistConfiguration getWhitelistConfiguration() {
        return whitelistConfiguration;
    }

    public void setWhitelistConfiguration(WhitelistConfiguration whitelistConfiguration) {
        this.whitelistConfiguration = whitelistConfiguration;
    }

    public TablistConfiguration getTablistConfiguration() {
        return tablistConfiguration;
    }
}
