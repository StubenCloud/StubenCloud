package eu.pixelstube.cloud.group;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.group.manager.ICloudGroupManager;
import eu.pixelstube.cloud.template.ITemplate;
import eu.pixelstube.cloud.type.GroupType;
import eu.pixelstube.cloud.type.GroupVersion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 21.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudGroupManager implements ICloudGroupManager {

    private final List<ICloudGroup> cloudGroups;

    public CloudGroupManager() {
        this.cloudGroups = new ArrayList<>();
    }

    public void fetchGroups() {

        CloudLauncher.getInstance().getDatabaseAdapter().executeQuery("SELECT * FROM cloud_groups", resultSet -> {

            try {

                while (resultSet.next()){
                    String name = resultSet.getString("name");
                    String maxServices = resultSet.getString("maxServices");
                    String minServices = resultSet.getString("minServices");
                    String maxMemory = resultSet.getString("maxMemory");
                    String percentageToStartService = resultSet.getString("percentage");
                    String groupName = resultSet.getString("groupType");
                    String maxPlayers = resultSet.getString("maxPlayers");
                    boolean maintenance = Boolean.parseBoolean(resultSet.getString("maintenance"));
                    //String template = resultSet.getString("template");

                    String display = resultSet.getString("groupVersion");

                    GroupType groupType = Arrays.stream(GroupType.values()).filter(groupType1 -> groupType1.getName().equalsIgnoreCase(groupName)).findAny().orElse(null);
                    GroupVersion groupVersion = Arrays.stream(GroupVersion.values()).filter(version -> version.getDisplay().equalsIgnoreCase(display)).findAny().orElse(null);

                    cloudGroups.add(new ICloudGroup() {
                        @Override
                        public UUID getUUID() {
                            return UUID.randomUUID();
                        }

                        @Override
                        public String getName() {
                            return name;
                        }

                        @Override
                        public int getMaxServices() {
                            return Integer.parseInt(maxServices);
                        }

                        @Override
                        public int getMinServices() {
                            return Integer.parseInt(minServices);
                        }

                        @Override
                        public int getMaxMemory() {
                            return Integer.parseInt(maxMemory);
                        }

                        @Override
                        public int getPercentageToStartNewService() {
                            return Integer.parseInt(percentageToStartService);
                        }

                        @Override
                        public GroupVersion getGroupVersion() {
                            return groupVersion;
                        }

                        @Override
                        public GroupType getGroupType() {
                            return groupType;
                        }

                        @Override
                        public boolean isMaintenance() {
                            return maintenance;
                        }

                        @Override
                        public int getMaxPlayers() {
                            return Integer.parseInt(maxPlayers);
                        }

                        @Override
                        public ITemplate getTemplate() {
                            return CloudLauncher.getInstance().getTemplateManager().getCachedTemplate("a");
                        }
                    });
                }

            }catch (SQLException exception){
                exception.printStackTrace();
            }

            return null;
        });

    }

    public void createGroup(String name, int maxServices, int minServices, int maxMemory, int percentage, GroupVersion groupVersion, GroupType groupType, String maintenance){
        CloudLauncher.getInstance().getDatabaseAdapter().executeUpdate(
                "INSERT INTO cloud_groups (name, maxServices, minServices, maxMemory, percentage, groupVersion, groupType, maintenance) VALUES " +
                        "('" + name + "', '" + maxServices + "', '" + minServices + "', '" +  maxMemory+ "', '" + percentage + "', '" +groupVersion + "', '" + groupType.getName() + "', '" + maintenance + "')"
        );
    }

    public List<ICloudGroup> getCloudGroups() {
        return cloudGroups;
    }
}
