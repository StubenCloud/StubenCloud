package eu.pixelstube.notify;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.logger.ICloudLogger;
import eu.pixelstube.cloud.module.ICloudModule;
import eu.pixelstube.cloud.module.Module;
import eu.pixelstube.notify.connection.ConnectionNode;

import java.io.File;

/*
    » This File was created by ZxroGame
    » Copyright© 2021 Clemens R.
    » Date: 29.05.2021
 */

@Module(name = "notify-module", reloadable = true)
public class NotifyModule implements ICloudModule {

    @Override
    public void onInitialization(final ICloudLogger cloudLogger) {

        JsonLib jsonLib = JsonLib.empty();

        jsonLib.append("prefix", "§8» §f§lProxy §8- ");
        jsonLib.append("service-started", "§8» §f§lProxy §8- §7Der Server §6§l%SERVICE% ist §a§lgestartet§8.");
        jsonLib.append("service-stopped", "§8» §f§lProxy §8- §7Der Server §6§l%SERVICE% ist §c§lgestopt§8.");

        if (!new File("modules/notify-module", "config.json").exists()) {
            jsonLib.saveAsFile(new File("modules/notify-module", "config.json"));

        }

        CloudLauncher.getInstance().getCloudConnectionServer().getServer().addListener(new ConnectionNode());

    }

    @Override
    public void onReload() {

    }
}