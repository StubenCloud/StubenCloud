package eu.pixelstube.cloud.commands;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.command.Command;
import eu.pixelstube.cloud.command.ICommandHandler;
import eu.pixelstube.cloud.console.ICommandSender;
import eu.pixelstube.cloud.service.ICloudService;
import org.jline.reader.Candidate;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 15.05.2021
 * Copyright© 2021 Max H.
 **/
@Command(name = "stop", description = "Stops the service")
public class StopCommand implements ICommandHandler {

    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {
        if(args[0].equalsIgnoreCase("stop")){

            iCommandSender.sendMessage("The cloud is stopping...");

            for (ICloudService cloudService : CloudLauncher.getInstance().getCloudServiceManager().getCloudServices()) {
                cloudService.getServiceExecutor().stop();
                cloudService.getServiceExecutor().shutdown();
            }

            CloudLauncher.getInstance().getCloudConnectionServer().getServer().stop();
            CloudLauncher.getInstance().getConsoleManager().stopThread();
            System.exit(1);

        }
    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }

}
