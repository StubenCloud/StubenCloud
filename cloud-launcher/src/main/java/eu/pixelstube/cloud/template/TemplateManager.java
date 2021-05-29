package eu.pixelstube.cloud.template;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.template.type.TemplateType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 26.05.2021
 * Copyright© 2021 Max H.
 **/
public class TemplateManager implements ITemplateManager{

    private static final List<ITemplate> templates = new ArrayList<>();

    public void fetchTemplates(){

        CloudLauncher.getInstance().getDatabaseAdapter().executeQuery("SELECT * FROM cloud_templates", resultSet -> {

            try {
                while(resultSet.next()){

                    UUID uuid = UUID.fromString(resultSet.getString("uniqueId"));
                    String name = resultSet.getString("name");
                    String type = resultSet.getString("type");

                    ITemplate template = new ITemplate() {
                        @Override
                        public UUID getUUID() {
                            return uuid;
                        }

                        @Override
                        public String getName() {
                            return name;
                        }

                        @Override
                        public TemplateType getType() {
                            return Arrays.stream(TemplateType.values()).filter(templateType -> templateType.name().equalsIgnoreCase(type)).findAny().orElse(null);
                        }

                    };

                    if (!templates.contains(template)) {
                        templates.add(template);
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });

    }

    @Override
    public ITemplate getCachedTemplate(String name) {
        return templates.stream().filter(iTemplate -> iTemplate.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    @Override
    public List<ITemplate> getTemplates() {
        return templates;
    }

}
