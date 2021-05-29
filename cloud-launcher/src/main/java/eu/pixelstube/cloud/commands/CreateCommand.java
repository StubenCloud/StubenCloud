package eu.pixelstube.cloud.commands;

import eu.pixelstube.cloud.command.Command;
import eu.pixelstube.cloud.command.ICommandHandler;
import eu.pixelstube.cloud.console.ICommandSender;
import eu.pixelstube.cloud.setups.GroupSetup;
import eu.pixelstube.cloud.setups.TemplateSetup;
import org.jline.reader.Candidate;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 21.05.2021
 * Copyright© 2021 Max H.
 **/
@Command(name = "create", description = "Creates a group or a template")
public class CreateCommand implements ICommandHandler {
    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {

        if(args[0].equalsIgnoreCase("create")){

            if(args.length == 2){
                if(args[1].equalsIgnoreCase("group")){

                    new GroupSetup();

                } else if (args[1].equalsIgnoreCase("template")) {

                    new TemplateSetup();

                }
            }

        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
