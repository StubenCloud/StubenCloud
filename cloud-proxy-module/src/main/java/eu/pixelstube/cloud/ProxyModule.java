package eu.pixelstube.cloud;

import eu.pixelstube.cloud.connection.ConnectionListener;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.logger.ICloudLogger;
import eu.pixelstube.cloud.module.ICloudModule;
import eu.pixelstube.cloud.module.Module;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
@Module(name = "proxy-module", authors = "Haizoooon", reloadable = true)
public class ProxyModule implements ICloudModule {

    public void onInitialization(ICloudLogger loggerProvider){

        JsonLib jsonLib = JsonLib.empty();

        jsonLib.append("prefix", "§8» §f§lProxy §8- §7");
        jsonLib.append("maintenance-version", "§8» §c§lMaintenance§8...");

        List<String> header = new ArrayList<>();
        header.add(" ");
        header.add("§8§l»§r §e§lStubenCloud §8▏ §7your §efree §7CloudSystem §8§l«");
        header.add("§e§l%players%§8/§e§l%max_players% §8▏ §e§l%service_name%");
        header.add(" ");

        List<String> footer = new ArrayList<>();
        footer.add(" ");
        footer.add("§8§l»§r §7Discord §8▏ §ediscord.gg/7dsf2ns");
        footer.add("§8§l»§r §7Partner §8▏ §eKernelHost.DE");
        footer.add(" ");

        List<String> maintenance = new ArrayList<>();
        maintenance.add("§8§l»§r §e§lStubenCloud §8▏ §7your §eCloudSystem §8▏ §e§l1.8.X");
        maintenance.add(" §4§l✖ §8▏ §c§lMaintenance§8...");

        List<String> online = new ArrayList<>();
        online.add("§8§l»§r §e§lStubenCloud §8▏ §7your §eCloudSystem §8▏ §e§l1.8.X");
        online.add(" §a§l✔ §8▏ §a§lOnline§8!");

        jsonLib.append("tablistConfiguration", JsonLib.empty().append("header", JsonLib.fromObject(header)).append("footer", JsonLib.fromObject(footer)));
        jsonLib.append("motdConfiguration", JsonLib.empty().append("maintenance", JsonLib.fromObject(maintenance)).append("online", JsonLib.fromObject(online)));

        List<String> whitelist = new ArrayList<>();
        whitelist.add("YyTFlo");
        whitelist.add("Haizoooon");
        whitelist.add("HttxDeVii");
        jsonLib.append("whitelist", JsonLib.fromObject(whitelist));

        if(!new File("modules/proxy-module", "config.json").exists()){
            jsonLib.saveAsFile("modules/proxy-module/config.json");
        }

        CloudLauncher.getInstance().getCloudConnectionServer().getServer().addListener(new ConnectionListener());

    }

    public void onReload(){
        try {
            String content = new String(Files.readAllBytes(Paths.get(new File("modules/proxy-module", "config.json").toURI())), StandardCharsets.UTF_8);

            CloudLauncher.getInstance().getCloudConnectionServer().getServer().sendToAllTCP(JsonLib.empty().append("type", "proxyModule").append("value", content).getAsJsonString());

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

}
