package eu.pixelstube.cloud;

import eu.pixelstube.cloud.connection.CloudConnection;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.service.ICloudService;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudPlugin {

    private static CloudPlugin instance;
    private final CloudConnection connection;
    private String serviceName;

    public CloudPlugin() {
        instance = this;

        new CloudAPI();

        connection = new CloudConnection();

        try {
            String content = new String(Files.readAllBytes(Paths.get(new File("cloud.json").toURI())), StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(content);

            serviceName = jsonObject.getString("serviceName");

            connection.getClient().sendTCP(JsonLib.empty().append("type", "service_started").append("serviceName", serviceName).getAsJsonString());

        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public ICloudService thisService(){
        return CloudAPI.getInstance().getCloudServiceManager().getCachedCloudService(serviceName);
    }

    public static CloudPlugin getInstance() {
        return instance;
    }

    public CloudConnection getConnection() {
        return connection;
    }
}
