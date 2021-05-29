package eu.pixelstube.cloud.bootstrap.spigot;

import eu.pixelstube.cloud.CloudPlugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class SpigotBootstrap extends JavaPlugin {

    private static SpigotBootstrap instance;

    @Override
    public void onEnable() {
        instance = this;

        new CloudPlugin();

    }

    public static SpigotBootstrap getInstance() {
        return instance;
    }
}
