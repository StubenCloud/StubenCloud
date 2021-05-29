package eu.pixelstube.notify.connection;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.notify.bootstrap.bungee.BungeeModule;
import eu.pixelstube.notify.bootstrap.bungee.MessageConfiguration;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ConnectionNode extends Listener {

    @Override
    public void received(Connection connection, Object object) {

        if(object.toString().charAt(0) == '{'){

            JSONObject jsonObject = new JSONObject(object.toString());

            if(jsonObject.getString("type").equalsIgnoreCase("service_started")){

                try {
                    String content = new String(Files.readAllBytes(Paths.get(new File(
                            "modules/notify-module", "config.json").toURI())), StandardCharsets.UTF_8);

                    CloudLauncher.getInstance().getCloudConnectionServer().getServer().sendToAllTCP(JsonLib.empty().append(
                            "type", "notifyModule").append("value", content).getAsJsonString());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            } else if(jsonObject.getString("type").equalsIgnoreCase("notifyModule")){

                JSONObject value = new JSONObject(jsonObject.getString("value"));

                if(CloudPlugin.getInstance().thisService().getCloudGroup().getGroupType().isProxy()){

                    BungeeModule.getInstance().setMessageConfiguration(
                            new MessageConfiguration(
                                    value.getString("prefix"), value.getString("service-started"), value.getString("service-stopped")));

                }

            }

        }

    }

}
