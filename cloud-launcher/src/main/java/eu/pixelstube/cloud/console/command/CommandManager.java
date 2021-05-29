package eu.pixelstube.cloud.console.command;

/*

  » de.thundercloud.launcher.console.command

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 18.04.2021 / 21:24

 */

import eu.pixelstube.cloud.command.Command;
import eu.pixelstube.cloud.command.ICommandHandler;

import java.util.HashMap;
import java.util.Map;


public class CommandManager {

    private final Map<Command, ICommandHandler> commandHandlers;

    public CommandManager() {
        this.commandHandlers = new HashMap<>();
    }

    @SafeVarargs
    public final void register(Class<? extends ICommandHandler>... command) {
        try {
            for (Class<? extends ICommandHandler> aClass : command) {
                commandHandlers.put(aClass.getAnnotation(Command.class), aClass.newInstance());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public ICommandHandler getCommandHandlerByName(String command) {
        return commandHandlers.get(commandHandlers.keySet().stream().filter(command1 -> command1.name().equalsIgnoreCase(command)).findAny().orElse(null));
    }

    public Map<Command, ICommandHandler> getCommandHandlers() {
        return commandHandlers;
    }

}
