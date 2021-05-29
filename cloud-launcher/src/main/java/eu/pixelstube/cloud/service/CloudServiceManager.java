package eu.pixelstube.cloud.service;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.player.ICloudPlayer;
import eu.pixelstube.cloud.service.executor.CloudServiceExecutor;
import eu.pixelstube.cloud.service.executor.ICloudServiceExecutor;
import eu.pixelstube.cloud.service.manager.ICloudServiceManager;
import eu.pixelstube.cloud.service.status.CloudServiceStatus;
import eu.pixelstube.cloud.type.GroupVersion;
import eu.pixelstube.cloud.url.DownloadType;
import eu.pixelstube.cloud.url.UrlDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 21.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudServiceManager implements ICloudServiceManager {

    private final List<ICloudService> cloudServices;

    public CloudServiceManager() {
        this.cloudServices = new ArrayList<>();
    }

    public void fetchServices(){

        CloudLauncher.getInstance().getCloudGroupManager().getCloudGroups().forEach(iCloudGroup -> {
            for(int i = 0; i < iCloudGroup.getMinServices(); i++){
                int finalI = i + 1;
                CloudLauncher.getInstance().getCloudLogger().info("Told MainWrapper to start service " + iCloudGroup.getName() + "-" + finalI);
                int port = randomPort();
                ICloudService cloudService = new ICloudService() {

                    private CloudServiceStatus cloudServiceStatus = CloudServiceStatus.PREPARING;
                    private final CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
                    private final CloudServiceExecutor cloudServiceExecutor = new CloudServiceExecutor(this);
                    private final UUID uuid = UUID.randomUUID();

                    @Override
                    public String getGroupName() {
                        return iCloudGroup.getName();
                    }

                    @Override
                    public int getServiceId() {
                        return finalI;
                    }

                    @Override
                    public UUID getUniqueId() {
                        return uuid;
                    }

                    @Override
                    public String getName() {
                        return iCloudGroup.getName();
                    }

                    @Override
                    public List<ICloudPlayer> getCurrentPlayers() {
                        return new ArrayList<>();
                    }

                    @Override
                    public ICloudGroup getCloudGroup() {
                        return iCloudGroup;
                    }

                    @Override
                    public CloudServiceStatus getServiceStatus() {
                        return cloudServiceStatus;
                    }

                    @Override
                    public ICloudServiceExecutor getServiceExecutor() {
                        return cloudServiceExecutor;
                    }

                    @Override
                    public CopyOnWriteArrayList<String> getConsoleMessages() {
                        return copyOnWriteArrayList;
                    }

                    @Override
                    public GroupVersion getVersion() {
                        return iCloudGroup.getGroupVersion();
                    }

                    @Override
                    public void setStatus(CloudServiceStatus cloudServiceStatus) {
                        this.cloudServiceStatus = cloudServiceStatus;
                    }

                    @Override
                    public String getServiceIdName() {
                        return getName() + "-" + getServiceId();
                    }

                    @Override
                    public int getPort() {
                        return port;
                    }

                    @Override
                    public boolean isStatic() {
                        return false;
                    }

                    @Override
                    public void start() {
                        CloudLauncher.getInstance().getFileManager().deleteFiles(new File("temp/" + getServiceIdName()));

                        if(!CloudLauncher.getInstance().getFileManager().fileExist("storage/jars", getVersion().getDisplay() + ".jar")){
                            new UrlDownloader(getVersion().getLink(), new File("storage/jars", getVersion().getDisplay() + ".jar"), DownloadType.MINECRAFT_JAR).download();
                        }

                        CloudLauncher.getInstance().getFileManager().createFolder("temp/" + getServiceIdName());
                        CloudLauncher.getInstance().getFileManager().copyFile("storage/jars/" + getVersion().getDisplay() + ".jar", "temp/" + getServiceIdName() + "/" + getVersion().getDisplay() + ".jar");


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

                        CloudLauncher.getInstance().getCloudConnectionServer().getServer().sendToAllTCP(jsonLib.getAsJsonString());

                    }
                };

                cloudServices.add(cloudService);

            }
        });

        cloudServices.forEach(cloudService -> {
            cloudService.start();

            CloudServiceExecutor cloudServiceExecutor = new CloudServiceExecutor(cloudService);
            new Thread(cloudServiceExecutor::start).start();

            try {
                Thread.sleep(2 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

    }

    private int randomPort(){
        return new Random().nextInt(40000 - 10000 + 1) + 10000;
    }

    @Override
    public ICloudService getCachedCloudService(String name) {
        return cloudServices.stream().filter(cloudService -> cloudService.getServiceIdName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public List<ICloudService> getCloudServices() {
        return cloudServices;
    }
}
