package eu.pixelstube.cloud.commands;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.backend.module.ThunderModule;
import eu.pixelstube.cloud.command.Command;
import eu.pixelstube.cloud.command.ICommandHandler;
import eu.pixelstube.cloud.console.ICommandSender;
import org.jline.reader.Candidate;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
@Command(name = "reload", description = "Reloads the cloud and the modules")
public class ReloadCommand implements ICommandHandler {
    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {

        for (ThunderModule module : CloudLauncher.getInstance().getModuleHandler().getModules()) {
            CloudLauncher.getInstance().getModuleHandler().reload(module);
        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
