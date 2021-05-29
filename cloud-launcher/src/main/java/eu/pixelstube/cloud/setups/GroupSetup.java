package eu.pixelstube.cloud.setups;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.console.setup.abstracts.SetupEnd;
import eu.pixelstube.cloud.console.setup.abstracts.SetupInput;
import eu.pixelstube.cloud.console.setup.interfaces.ISetup;
import eu.pixelstube.cloud.type.GroupType;
import eu.pixelstube.cloud.type.GroupVersion;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 21.05.2021
 * Copyright© 2021 Max H.
 **/
public class GroupSetup extends ISetup {

    private String name;

    public GroupSetup() {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

                CloudLauncher.getInstance().getCloudGroupManager().createGroup(name, 1, 1, 512, 100, null, GroupType.PROXY, "true");

            }
        });

        setSetupInputs(new SetupInput("Please provide the group name") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                name = input;
                return true;
            }
        });

    }
}
