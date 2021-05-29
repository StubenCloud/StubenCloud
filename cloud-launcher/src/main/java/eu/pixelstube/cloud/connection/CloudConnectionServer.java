package eu.pixelstube.cloud.connection;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.service.ICloudService;
import eu.pixelstube.cloud.service.status.CloudServiceStatus;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 21.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudConnectionServer {

    private final Server server;

    public CloudConnectionServer() {

        server = new Server();
        try {
            server.bind(16040, 16041);

            CloudLauncher.getInstance().getCloudLogger().info("The cloud is listening on port 16040");
            CloudLauncher.getInstance().getCloudLogger().info("The cloud is listening on port 16041");

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        server.start();

        server.getKryo().setRegistrationRequired(false);

        server.addListener(new Listener(){

            @Override
            public void received(Connection connection, Object o) {

                if(o.toString().charAt(0) == '{'){

                    JSONObject jsonObject = new JSONObject(o.toString());

                    if(jsonObject.getString("type").equalsIgnoreCase("service_started")){

                        CloudLauncher.getInstance().getCloudLogger().info("Cloud service " + jsonObject.getString("serviceName") + " logged in!");



                        ICloudService cloudService = CloudLauncher.getInstance().getCloudServiceManager().getCachedCloudService(jsonObject.getString("serviceName"));

                        cloudService.setStatus(CloudServiceStatus.STARTED);

                        for(ICloudGroup cloudGroup : CloudLauncher.getInstance().getCloudGroupManager().getCloudGroups()){

                            JsonLib jsonLib = JsonLib.empty();

                            jsonLib.append("type", "group_registered");
                            jsonLib.append("uuid", cloudGroup.getUUID().toString());
                            jsonLib.append("name", cloudGroup.getName());
                            jsonLib.append("maxServices", cloudGroup.getMaxServices());
                            jsonLib.append("minServices", cloudGroup.getMinServices());
                            jsonLib.append("maxMemory", cloudGroup.getMaxMemory());
                            jsonLib.append("percentage", cloudGroup.getPercentageToStartNewService());
                            jsonLib.append("groupVersion", cloudGroup.getGroupVersion().getDisplay());
                            jsonLib.append("groupType", cloudGroup.getGroupType().getName());
                            jsonLib.append("maintenance", cloudGroup.isMaintenance());
                            jsonLib.append("maxPlayers", cloudGroup.getMaxPlayers());

                            connection.sendTCP(jsonLib.getAsJsonString());

                        }

                        for (ICloudService service : CloudLauncher.getInstance().getCloudServiceManager().getCloudServices()) {
                            service.update();
                        }

                    } else if (jsonObject.getString("type").equalsIgnoreCase("player_connected")) {

                        String name = jsonObject.getString("name");
                        String uuid = jsonObject.getString("uuid");
                        String address = jsonObject.getString("address");

                        CloudLauncher.getInstance().getCloudLogger().info("Player " + name + " is connected. (" + uuid + "/" + address + ")");

                        CloudLauncher.getInstance().getDatabaseAdapter().executeQuery("SELECT * FROM cloud_players WHERE uniqueId = '" + uuid + "'", resultSet -> {
                            try {
                                if(!resultSet.next()){

                                    JsonLib jsonLib = JsonLib.empty();

                                    jsonLib.append("uniqueId", uuid);
                                    jsonLib.append("name", name);
                                    jsonLib.append("connection", JsonLib.empty().append("address", address).append("first", System.currentTimeMillis()));

                                    PreparedStatement statement = CloudLauncher.getInstance().getDatabaseAdapter().getConnection().prepareStatement("INSERT INTO cloud_players (name, uniqueId, info) VALUES (?, ?, ?)");

                                    statement.setString(1, name);
                                    statement.setString(2, uuid);
                                    statement.setString(3, jsonLib.getAsJsonString());

                                    statement.executeUpdate();

                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            return null;
                        });

                    }

                }

            }
        });

    }

    public Server getServer() {
        return server;
    }

}
