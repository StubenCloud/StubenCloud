package eu.pixelstube.cloud.console.setup;

/*

  » de.thundercloud.launcher.console.setup

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 31.03.2021 / 11:48

 */

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.console.setup.abstracts.SetupEnd;
import eu.pixelstube.cloud.console.setup.abstracts.SetupInput;
import eu.pixelstube.cloud.console.setup.interfaces.ISetup;

import java.util.ArrayList;
import java.util.List;

public class SetupBuilder {

    private static final List<ISetup> setupQueue = new ArrayList<>();

    private static SetupInput currentInput;
    private static SetupEnd setupEnd;
    private static SetupInput[] setupInputs;
    private static int currentIndex = 0;
    private static ISetup currentSetup;

    public SetupBuilder(ISetup setup,  SetupEnd setupEnd, SetupInput... setupInputs) {
        currentSetup = setup;
        setupQueue.add(currentSetup);
        SetupBuilder.setupEnd = setupEnd;
        SetupBuilder.setupInputs = setupInputs;
        currentInput = setupInputs[currentIndex];
        CloudLauncher.getInstance().getConsoleManager().getConsoleCompleter().setSuggestions(currentInput.getSuggestions());
        CloudLauncher.getInstance().getCloudLogger().setup(currentInput.getQuestion());
    }

    public static void nextQuestion(boolean value){
        if(value){
            if(currentIndex == setupInputs.length - 1){
                setupEnd.handle();
                currentSetup = null;
                setupQueue.clear();
                currentIndex = 0;
                CloudLauncher.getInstance().getCloudLogger().success("Setup was successfully completed.");
                List<String> commands = new ArrayList<>();
                CloudLauncher.getInstance().getCommandManager().getCommandHandlers().forEach((command, iCommandHandler) -> {
                    commands.add(command.name());
                });
                CloudLauncher.getInstance().getConsoleManager().getConsoleCompleter().setSuggestions(commands);
                return;
            }
            currentIndex = currentIndex + 1;
            currentInput = setupInputs[currentIndex];
        } else {
            CloudLauncher.getInstance().getCloudLogger().warning("The input was incorrect...");
        }
        CloudLauncher.getInstance().getConsoleManager().getConsoleCompleter().setSuggestions(currentInput.getSuggestions());
        CloudLauncher.getInstance().getCloudLogger().setup(currentInput.getQuestion());
        getCurrentSetup().setCurrentInput(currentInput);
    }

    public static ISetup getCurrentSetup() {
        return currentSetup;
    }

}
