package eu.pixelstube.cloud.setups;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.console.setup.abstracts.SetupEnd;
import eu.pixelstube.cloud.console.setup.abstracts.SetupInput;
import eu.pixelstube.cloud.console.setup.interfaces.ISetup;
import eu.pixelstube.cloud.template.ITemplate;
import eu.pixelstube.cloud.type.GroupType;
import eu.pixelstube.cloud.type.GroupVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 21.05.2021
 * Copyright© 2021 Max H.
 **/
public class GroupSetup extends ISetup {

    private String name, maxServices, minServices, maxMemory, percentage, groupVersionName, groupTypeName, template;

    public GroupSetup() {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

                CloudLauncher.getInstance().getCloudGroupManager().createGroup(name,
                        Integer.parseInt(maxServices),
                        Integer.parseInt(minServices),
                        Integer.parseInt(maxMemory),
                        Integer.parseInt(percentage),
                        Objects.requireNonNull(Arrays.stream(GroupVersion.values()).filter(groupVersion -> groupVersion.getDisplay().equalsIgnoreCase(groupVersionName)).findAny().orElse(null)),
                        Objects.requireNonNull(Arrays.stream(GroupType.values()).filter(groupType -> groupType.getName().equalsIgnoreCase(groupTypeName)).findAny().orElse(null)),
                        template,
                        "true");

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
        }, new SetupInput("Please provide the max service count") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                maxServices = input;
                return isInteger(input);
            }
        }, new SetupInput("Please provide the min service count") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                minServices = input;
                return isInteger(input);
            }
        }, new SetupInput("Please provide the max memory (MB)") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                maxMemory = input;
                return isInteger(input);
            }
        }, new SetupInput("Please provide the percentage to start a new service") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                percentage = input;
                return isInteger(input);
            }
        }, new SetupInput("Please provide the group type") {
            @Override
            public List<String> getSuggestions() {
                return Arrays.asList("Lobby", "Proxy", "Server");
            }

            @Override
            public boolean handle(String input) {
                groupTypeName = input;
                return getSuggestions().stream().anyMatch(s -> s.equalsIgnoreCase(input));
            }
        }, new SetupInput("Please provide the group version") {
            @Override
            public List<String> getSuggestions() {
                List<String> versionList = new ArrayList<>();
                if (Objects.equals(Arrays.stream(GroupType.values()).filter(type -> type.getName().equalsIgnoreCase(groupTypeName)).findAny().orElse(null), GroupType.LOBBY)) {
                    versionList.addAll(getGroupVersions(GroupType.SERVER));
                } else {
                    versionList.addAll(getGroupVersions(Arrays.stream(GroupType.values()).filter(type -> type.getName().equalsIgnoreCase(groupTypeName)).findAny().orElse(null)));
                }
                return versionList;
            }

            @Override
            public boolean handle(String input) {
                groupVersionName = input;
                return getSuggestions().stream().anyMatch(s -> s.equalsIgnoreCase(input));
            }
        }, new SetupInput("Please provide the template name") {
            @Override
            public List<String> getSuggestions() {
                List<String> templateList = new ArrayList<>();
                for (ITemplate iTemplate : CloudLauncher.getInstance().getTemplateManager().getTemplates()) {
                    templateList.add(iTemplate.getName());
                }
                return templateList;
            }

            @Override
            public boolean handle(String input) {
                template = input;
                return getSuggestions().stream().anyMatch(s -> s.equalsIgnoreCase(input));
            }
        });

    }

    private boolean isInteger(String value){
        return value.matches("[0-9]+");
    }

    public List<String> getGroupVersions(GroupType groupType){
        List<String> groupVersions = new ArrayList<>();
        for(GroupVersion groupVersion : GroupVersion.values()){
            if(groupVersion.getGroupType().equals(groupType)){
                groupVersions.add(groupVersion.getDisplay());
            }
        }
        return groupVersions;
    }

}
