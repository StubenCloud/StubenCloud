package eu.pixelstube.cloud.service.configuration.defaults;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.backend.file.FileEditor;
import eu.pixelstube.cloud.service.ICloudService;
import eu.pixelstube.cloud.service.configuration.IServiceConfiguration;

import java.io.File;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 26.05.2021
 * Copyright© 2021 Max H.
 **/
public class DefaultVelocityConfiguration implements IServiceConfiguration {
    @Override
    public void configure(ICloudService cloudService, File file) {
        File configFile = new File(file, "velocity.toml");
        if(!configFile.exists()){
            CloudLauncher.getInstance().getFileManager().copyFileOutOfJar(configFile, "/files/velocity.toml");
        }
        FileEditor fileEditor = new FileEditor(configFile);
        fileEditor.replaceLine("bind = \"0.0.0.0:25577\"", "bind = \"0.0.0.0:25565\"");
        fileEditor.replaceLine("show-max-players = 500", "show-max-players = " + cloudService.getCloudGroup().getMaxPlayers());
        fileEditor.save();

    }
}
