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
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
@Command(name = "info", description = "Info about a service")
public class InfoCommand implements ICommandHandler {
    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {

        if (args[0].equalsIgnoreCase("info")) {
            if(args.length == 2){

                ICloudService cloudService = CloudLauncher.getInstance().getCloudServiceManager().getCloudServices().stream().filter(cloudService1 -> cloudService1.getServiceIdName().equalsIgnoreCase(args[1])).findAny().orElse(null);

                if(cloudService == null){
                    CloudLauncher.getInstance().getCloudLogger().severe("The service " + args[1] + " doesn't exists...");
                    return;
                }

                iCommandSender.sendMessage(" ");
                iCommandSender.sendMessage("");

            }
        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
