package eu.pixelstube.cloud.setups;

import eu.pixelstube.cloud.backend.wrapper.WrapperManager;
import eu.pixelstube.cloud.console.setup.abstracts.SetupEnd;
import eu.pixelstube.cloud.console.setup.abstracts.SetupInput;
import eu.pixelstube.cloud.console.setup.interfaces.ISetup;

import java.util.Arrays;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 22.05.2021
 * Copyright© 2021 Max H.
 **/
public class WrapperSetup extends ISetup {

    private String memory, name, host;

    public WrapperSetup(WrapperManager wrapperManager) {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

//                wrapperManager.write(new JsonLib().append(name, "memory", memory).append(name, "host", host).getJson());

            }
        });

        setSetupInputs(new SetupInput("Please provide the wrapper name") {
            @Override
            public List<String> getSuggestions() {
                return Arrays.asList("LocalWrapper", "InternalWrapper");
            }

            @Override
            public boolean handle(String input) {
                name = input;
                return true;
            }
        }, new SetupInput("Please provide the wrapper host") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                host = input;
                return true;
            }
        }, new SetupInput("Please provide the maximum memory of the wrapper (MB)") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                memory = input;
                return input.matches("[0-9]+");
            }
        });

    }
}
