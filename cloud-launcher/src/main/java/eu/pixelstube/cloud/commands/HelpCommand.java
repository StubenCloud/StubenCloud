package eu.pixelstube.cloud.commands;

import eu.pixelstube.cloud.CloudLauncher;
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
@Command(name = "help", description = "Shows all commands with description")
public class HelpCommand implements ICommandHandler {
    @Override
    public void handle(ICommandSender iCommandSender, String[] args) {

        if (args[0].equalsIgnoreCase("help")) {

            iCommandSender.sendMessage(" ");

            for (Command command : CloudLauncher.getInstance().getCommandManager().getCommandHandlers().keySet()) {
                iCommandSender.sendMessage(command.name() + " | " + command.description());
            }

            iCommandSender.sendMessage(" ");

        }

    }

    @Override
    public List<Candidate> getSuggestions() {
        return null;
    }
}
