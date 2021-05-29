package eu.pixelstube.notify.bootstrap.bungee;

/*
//This File was created by ZxroGame
//Copyright© 2021 Clemens R.
//Date: 29.05.2021
 */

import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.notify.connection.ConnectionNode;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeModule extends Plugin {

    private static BungeeModule instance;
    private MessageConfiguration messageConfiguration;

    @Override
    public void onEnable() {

        instance = this;

        CloudPlugin.getInstance().getConnection().getClient().addListener(new ConnectionNode());

    }

    public MessageConfiguration getMessageConfiguration() {
        return messageConfiguration;
    }

    public void setMessageConfiguration(MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
    }

    public static BungeeModule getInstance() {
        return instance;
    }
}
