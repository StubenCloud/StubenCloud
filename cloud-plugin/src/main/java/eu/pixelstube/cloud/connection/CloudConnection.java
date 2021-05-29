package eu.pixelstube.cloud.connection;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import eu.pixelstube.cloud.CloudAPI;
import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.bootstrap.bungeecord.BungeeBootstrap;
import eu.pixelstube.cloud.bootstrap.bungeecord.events.BungeeCloudServiceStartEvent;
import eu.pixelstube.cloud.bootstrap.bungeecord.events.BungeeCloudServiceStopEvent;
import eu.pixelstube.cloud.bootstrap.bungeecord.events.BungeeCloudServiceUpdateEvent;
import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.player.ICloudPlayer;
import eu.pixelstube.cloud.service.ICloudService;
import eu.pixelstube.cloud.service.executor.ICloudServiceExecutor;
import eu.pixelstube.cloud.service.status.CloudServiceStatus;
import eu.pixelstube.cloud.template.ITemplate;
import eu.pixelstube.cloud.type.GroupType;
import eu.pixelstube.cloud.type.GroupVersion;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudConnection {

    private final Client client;

    public CloudConnection() {

        this.client = new Client();
        client.start();
        try {
            client.connect(5019, "127.0.0.1", 16040, 16041);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        client.addListener(new Listener() {

            @Override
            public void received(Connection connection, Object o) {

                if (o.toString().charAt(0) == '{') {

                    JSONObject jsonObject = new JSONObject(o.toString());

                    if (jsonObject.getString("type").equalsIgnoreCase("service_update")) {

                        String groupName = jsonObject.getString("groupName");
                        int serviceId = jsonObject.getInt("serviceId");
                        UUID uuid = UUID.fromString(jsonObject.getString("uuid"));
                        String name = jsonObject.getString("name");
                        String serviceIdName = name + "-" + serviceId;
                        CloudServiceStatus cloudServiceStatus = Arrays.stream(CloudServiceStatus.values()).filter(cloudServiceStatus1 -> cloudServiceStatus1.name().equalsIgnoreCase(jsonObject.getString("cloudStatus"))).findAny().orElse(null);
                        int port = jsonObject.getInt("port");
                        boolean staticService = jsonObject.getBoolean("static");
                        GroupVersion groupVersion = Arrays.stream(GroupVersion.values()).filter(groupVersion1 -> groupVersion1.getDisplay().equalsIgnoreCase(jsonObject.getString("groupVersion"))).findAny().orElse(null);

                        ICloudService cloudService = new ICloudService() {
                            @Override
                            public String getGroupName() {
                                return groupName;
                            }

                            @Override
                            public int getServiceId() {
                                return serviceId;
                            }

                            @Override
                            public UUID getUniqueId() {
                                return uuid;
                            }

                            @Override
                            public String getName() {
                                return name;
                            }

                            @Override
                            public String getServiceIdName() {
                                return serviceIdName;
                            }

                            @Override
                            public List<ICloudPlayer> getCurrentPlayers() {
                                return new ArrayList<>();
                            }

                            @Override
                            public ICloudGroup getCloudGroup() {
                                return CloudAPI.getInstance().getCloudGroupManager().getCloudGroups().stream().filter(iCloudGroup -> iCloudGroup.getName().equalsIgnoreCase(groupName)).findAny().orElse(null);
                            }

                            @Override
                            public CloudServiceStatus getServiceStatus() {
                                return cloudServiceStatus;
                            }

                            @Override
                            public ICloudServiceExecutor getServiceExecutor() {
                                return null;
                            }

                            @Override
                            public CopyOnWriteArrayList<String> getConsoleMessages() {
                                return new CopyOnWriteArrayList<>();
                            }

                            @Override
                            public GroupVersion getVersion() {
                                return groupVersion;
                            }

                            @Override
                            public int getPort() {
                                return port;
                            }

                            @Override
                            public void setStatus(CloudServiceStatus cloudServiceStatus) {

                            }

                            @Override
                            public boolean isStatic() {
                                return staticService;
                            }

                            @Override
                            public void start() {

                            }

                            @Override
                            public void update() {
                                JsonLib jsonLib = JsonLib.empty();

                                jsonLib.append("type", "service_update");
                                jsonLib.append("groupName", getGroupName());
                                jsonLib.append("serviceId", getServiceId());
                                jsonLib.append("uuid", getUniqueId().toString());
                                jsonLib.append("name", getName());
                                jsonLib.append("port", getPort());
                                jsonLib.append("cloudStatus", getServiceStatus().name());
                                jsonLib.append("static", isStatic());
                                jsonLib.append("groupVersion", getVersion().getDisplay());

                                connection.sendTCP(jsonLib.getAsJsonString());
                            }
                        };

                        CloudAPI.getInstance().getCloudServiceManager().getCloudServices().remove(CloudAPI.getInstance().getCloudServiceManager().getCloudServices().stream().filter(cloudService1 -> cloudService1.getName().equalsIgnoreCase(name)).findAny().orElse(null));
                        CloudAPI.getInstance().getCloudServiceManager().getCloudServices().add(cloudService);

                        System.out.println(cloudService.getCloudGroup().getGroupType().name());
                        System.out.println(cloudService.getCloudGroup().getGroupVersion().name());

                        if (CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.WATERFALL) || CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.BUNGEECORD)) {

                            BungeeBootstrap.getInstance().getProxy().getPluginManager().callEvent(new BungeeCloudServiceUpdateEvent(cloudService));

                        }

                    } else if (jsonObject.getString("type").equalsIgnoreCase("service_registered")) {

                        ICloudService cloudService = CloudAPI.getInstance().getCloudServiceManager().getCachedCloudService(jsonObject.getString("serviceName"));

                        System.out.println(cloudService.getCloudGroup().getGroupType().name());
                        System.out.println(cloudService.getCloudGroup().getGroupVersion().name());

                        if (cloudService.getCloudGroup().getGroupType().equals(GroupType.SERVER) || cloudService.getCloudGroup().getGroupType().equals(GroupType.LOBBY)) {
                            if (CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.WATERFALL) || CloudPlugin.getInstance().thisService().getVersion().equals(GroupVersion.BUNGEECORD)) {

                                BungeeBootstrap.getInstance().registerService(cloudService.getServiceIdName(), new InetSocketAddress("127.0.0.1", cloudService.getPort()));

                                BungeeBootstrap.getInstance().getProxy().getPluginManager().callEvent(new BungeeCloudServiceStartEvent(cloudService));

                            }

                        }

                    } else if (jsonObject.getString("type").equalsIgnoreCase("group_registered")) {

                        UUID uuid = UUID.fromString(jsonObject.getString("uuid"));
                        String name = jsonObject.getString("name");
                        int maxServices = jsonObject.getInt("maxServices");
                        int minServices = jsonObject.getInt("minServices");
                        int maxMemory = jsonObject.getInt("maxMemory");
                        int percentage = jsonObject.getInt("percentage");
                        GroupVersion groupVersion = Arrays.stream(GroupVersion.values()).filter(groupVersion1 -> groupVersion1.getDisplay().equalsIgnoreCase(jsonObject.getString("groupVersion"))).findAny().orElse(null);
                        GroupType groupType = Arrays.stream(GroupType.values()).filter(groupType1 -> groupType1.getName().equalsIgnoreCase(jsonObject.getString("groupType"))).findAny().orElse(null);
                        boolean maintenance = jsonObject.getBoolean("maintenance");
                        int maxPlayers = jsonObject.getInt("maxPlayers");

                        ICloudGroup cloudGroup = new ICloudGroup() {
                            @Override
                            public UUID getUUID() {
                                return uuid;
                            }

                            @Override
                            public String getName() {
                                return name;
                            }

                            @Override
                            public int getMaxServices() {
                                return maxServices;
                            }

                            @Override
                            public int getMinServices() {
                                return minServices;
                            }

                            @Override
                            public int getMaxMemory() {
                                return maxMemory;
                            }

                            @Override
                            public int getPercentageToStartNewService() {
                                return percentage;
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
                                return maxPlayers;
                            }

                            @Override
                            public ITemplate getTemplate() {
                                return null;
                            }
                        };

                        if (!CloudAPI.getInstance().getCloudGroupManager().getCloudGroups().contains(cloudGroup)) {
                            CloudAPI.getInstance().getCloudGroupManager().getCloudGroups().add(cloudGroup);
                        }

                    } else if (jsonObject.getString("type").equalsIgnoreCase("service_unregistered")) {

                        ICloudService cloudService = CloudAPI.getInstance().getCloudServiceManager().getCachedCloudService(jsonObject.getString("serviceName"));

                        System.out.println(cloudService.getCloudGroup().getGroupType().name());
                        System.out.println(cloudService.getCloudGroup().getGroupVersion().name());

                        BungeeBootstrap.getInstance().getProxy().getPluginManager().callEvent(new BungeeCloudServiceStopEvent(cloudService));

                    }

                }


            }

        });

    }

    public Client getClient() {
        return client;
    }
}
