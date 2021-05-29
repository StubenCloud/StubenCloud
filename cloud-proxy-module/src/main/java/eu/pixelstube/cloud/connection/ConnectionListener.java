package eu.pixelstube.cloud.connection;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.configuration.MessageConfiguration;
import eu.pixelstube.cloud.configuration.MotdConfiguration;
import eu.pixelstube.cloud.configuration.TablistConfiguration;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.proxy.bungee.BungeeBootstrap;
import org.json.JSONObject;

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
public class ConnectionListener extends Listener {

    @Override
    public void received(Connection connection, Object o) {

        if(o.toString().charAt(0) == '{'){

            JSONObject jsonObject = new JSONObject(o.toString());

            if (jsonObject.getString("type").equalsIgnoreCase("service_started")) {

                try {
                    String content = new String(Files.readAllBytes(Paths.get(new File("modules/proxy-module", "config.json").toURI())), StandardCharsets.UTF_8);

                    CloudLauncher.getInstance().getCloudConnectionServer().getServer().sendToAllTCP(JsonLib.empty().append("type", "proxyModule").append("value", content).getAsJsonString());

                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            } else if(jsonObject.getString("type").equalsIgnoreCase("proxyModule")){

                JSONObject value = new JSONObject(jsonObject.getString("value"));

                String prefix = value.getString("prefix");
                String maintenance_version = value.getString("maintenance-version");

                BungeeBootstrap.getInstance().setMessageConfiguration(new MessageConfiguration(prefix, maintenance_version));

                JSONObject tablist = value.getJSONObject("tablistConfiguration");

                List<Object> headerRaw = tablist.getJSONArray("header").toList();
                List<Object> footerRaw = tablist.getJSONArray("footer").toList();

                List<String> header = new ArrayList<>();
                for (Object object : headerRaw) {
                    header.add(object.toString());
                }
                List<String> footer = new ArrayList<>();
                for(Object object : footerRaw){
                    footer.add(object.toString());
                }

                BungeeBootstrap.getInstance().setTablistConfiguration(new TablistConfiguration(header, footer));

                BungeeBootstrap.getInstance().start();

                JSONObject motd = value.getJSONObject("motdConfiguration");

                List<Object> maintenanceRaw = motd.getJSONArray("maintenance").toList();
                List<Object> onlineRaw = motd.getJSONArray("online").toList();

                List<String> maintenance = new ArrayList<>();
                for(Object object : maintenanceRaw){
                    maintenance.add(object.toString());
                }

                List<String> online = new ArrayList<>();
                for(Object object : onlineRaw){
                    online.add(object.toString());
                }

                BungeeBootstrap.getInstance().setMotdConfiguration(new MotdConfiguration(maintenance, online));

            }

        }

    }
}
