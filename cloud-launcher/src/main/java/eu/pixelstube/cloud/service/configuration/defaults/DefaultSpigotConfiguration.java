package eu.pixelstube.cloud.service.configuration.defaults;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.backend.file.FileEditor;
import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.service.ICloudService;
import eu.pixelstube.cloud.service.configuration.IServiceConfiguration;

import java.io.File;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class DefaultSpigotConfiguration implements IServiceConfiguration {

    @Override
    public void configure(ICloudService cloudService, File file) {

        File newFile = new File(file, "server.properties");

        if(!newFile.exists()){
            CloudLauncher.getInstance().getFileManager().copyFileOutOfJar(newFile, "/files/server.properties");
        }

        ICloudGroup cloudGroup = CloudLauncher.getInstance().getCloudGroupManager().getCloudGroups().stream().filter(iCloudGroup -> iCloudGroup.getName().equalsIgnoreCase(cloudService.getName())).findAny().orElse(null);

        if(cloudGroup == null) return;

        FileEditor fileEditor = new FileEditor(newFile);
        fileEditor.replaceLine("server-ip=127.0.0.1", "server-ip=127.0.0.1");
        fileEditor.replaceLine("server-port=25565", "server-port=" + cloudService.getPort());
        fileEditor.replaceLine("max-players=20", "max-players=" + cloudGroup.getMaxPlayers());
        fileEditor.save();

    }

}
