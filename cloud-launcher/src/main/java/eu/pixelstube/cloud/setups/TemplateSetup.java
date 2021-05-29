package eu.pixelstube.cloud.setups;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.console.setup.abstracts.SetupEnd;
import eu.pixelstube.cloud.console.setup.abstracts.SetupInput;
import eu.pixelstube.cloud.console.setup.interfaces.ISetup;
import eu.pixelstube.cloud.template.type.TemplateType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
public class TemplateSetup extends ISetup {

    private String name;
    private String type;

    public TemplateSetup() {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

                TemplateType templateType = Arrays.stream(TemplateType.values()).filter(templateType1 -> templateType1.name().equalsIgnoreCase(type.toUpperCase())).findAny().orElse(null);
                UUID uuid = UUID.randomUUID();

                assert templateType != null;
                CloudLauncher.getInstance().getDatabaseAdapter().executeUpdate("INSERT INTO cloud_templates (name, uniqueId, type) VALUES ('" + name + "', '" + uuid + "', '" + templateType.name() + "')");

            }
        });

        setSetupInputs(new SetupInput("Provide the template name") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                name = input.replace(" ", "");
                return CloudLauncher.getInstance().getTemplateManager().getTemplates().stream().noneMatch(iTemplate -> iTemplate.getName().equalsIgnoreCase(name));
            }
        }, new SetupInput("Please provide the template type. (Static, Dynamic, Template)") {
            @Override
            public List<String> getSuggestions() {
                return Arrays.asList("Static", "Dynamic", "Template");
            }

            @Override
            public boolean handle(String input) {
                type = input.replace(" ", "");
                return getSuggestions().contains(type);
            }
        });

    }
}
