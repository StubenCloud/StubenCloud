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
public class DefaultBungeeConfiguration implements IServiceConfiguration {
    @Override
    public void configure(ICloudService cloudService, File file) {
        File newFile = new File(file, "config.yml");
        if(!newFile.exists()){
            CloudLauncher.getInstance().getFileManager().copyFileOutOfJar(newFile, "/files/config.yml");
        }
        if(!new File("storage", "server-icon.png").exists()){
            CloudLauncher.getInstance().getFileManager().copyFileOutOfJar(new File(file, "server-icon.png"), "/files/server-icon.png");
        }
        ICloudGroup cloudGroupService = CloudLauncher.getInstance().getCloudGroupManager().getCloudGroups().stream().filter(cloudGroup -> cloudGroup.getName().equalsIgnoreCase(cloudGroup.getName())).findAny().orElse(null);

        if(cloudGroupService == null){
            return;
        }

        FileEditor fileEditor = new FileEditor(newFile);
        fileEditor.replaceLine("  host: 0.0.0.0:25577", "  host: 0.0.0.0:25565");
        fileEditor.replaceLine("  max_players: 1", "  max_players: " +  cloudGroupService.getMaxPlayers());
        fileEditor.save();
    }
}
