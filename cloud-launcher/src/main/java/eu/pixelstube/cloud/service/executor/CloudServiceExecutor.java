package eu.pixelstube.cloud.service.executor;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.backend.module.ThunderModule;
import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.jsonlib.JsonLib;
import eu.pixelstube.cloud.service.ICloudService;
import eu.pixelstube.cloud.service.configuration.defaults.DefaultBungeeConfiguration;
import eu.pixelstube.cloud.service.configuration.defaults.DefaultSpigotConfiguration;
import eu.pixelstube.cloud.service.configuration.defaults.DefaultVelocityConfiguration;
import eu.pixelstube.cloud.service.status.CloudServiceStatus;
import eu.pixelstube.cloud.template.type.TemplateType;
import eu.pixelstube.cloud.type.GroupVersion;
import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 22.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudServiceExecutor implements ICloudServiceExecutor{

    private final ICloudService cloudService;
    private static Process process;

    public CloudServiceExecutor(ICloudService cloudService) {
        this.cloudService = cloudService;
    }

    @Override
    public void start() {

        try {

            cloudService.setStatus(CloudServiceStatus.STARTING);

            if(cloudService.getVersion().equals(GroupVersion.BUNGEECORD) || cloudService.getVersion().equals(GroupVersion.WATERFALL)){
                DefaultBungeeConfiguration configuration = new DefaultBungeeConfiguration();
                configuration.configure(cloudService, new File("temp/" + cloudService.getServiceIdName()));
            } else if(cloudService.getVersion().equals(GroupVersion.VELOCITY)) {
                DefaultVelocityConfiguration configuration = new DefaultVelocityConfiguration();
                configuration.configure(cloudService, new File("temp/" + cloudService.getServiceIdName()));
            } else {
                DefaultSpigotConfiguration configuration = new DefaultSpigotConfiguration();
                configuration.configure(cloudService, new File("temp/" + cloudService.getServiceIdName()));
            }

            if (cloudService.getCloudGroup().getTemplate().getType().equals(TemplateType.TEMPLATE)) {

                if (!CloudLauncher.getInstance().getFileManager().folderExist("template/" + cloudService.getGroupName())) {
                    CloudLauncher.getInstance().getFileManager().createFolder("template/" + cloudService.getGroupName());
                }

                CloudLauncher.getInstance().getFileManager().copyDirectory("template/" + cloudService.getGroupName(), "temp/" + cloudService.getServiceIdName());
            }

            CloudLauncher.getInstance().getFileManager().createFolder("temp/" + cloudService.getServiceIdName() + "/plugins");
            CloudLauncher.getInstance().getFileManager().copyFile("storage/jars/cloud-plugin.jar", "temp/" + cloudService.getServiceIdName() + "/plugins/cloud-plugin.jar");

            for (ThunderModule module : CloudLauncher.getInstance().getModuleHandler().getModules()) {
                CloudLauncher.getInstance().getFileManager().copyFile("modules/" + module.getName() + ".jar", "temp/" + cloudService.getServiceIdName() + "/plugins/" + module.getName() + ".jar");
            }

            JsonLib jsonLib = JsonLib.empty();

            jsonLib.append("serviceName", cloudService.getServiceIdName());

            try {

                File file = new File("temp/" + cloudService.getServiceIdName(), "cloud.json");
                if(!file.exists()){
                    file.createNewFile();
                }

                FileWriter fileWriter = new FileWriter(file);

                fileWriter.write(jsonLib.getAsJsonString());
                fileWriter.flush();

            } catch (IOException | JSONException exception) {
                exception.printStackTrace();
            }

            process = createProcessBuilder().directory(new File("temp/" + cloudService.getServiceIdName())).start();

            Thread thread = new Thread(() -> {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                while (process.isAlive()) {
                    try {

                        String string;

                        if ("".equalsIgnoreCase(bufferedReader.readLine())) {
                            return;
                        } else {
                            string = bufferedReader.readLine();
                        }

                        this.cloudService.getConsoleMessages().add(string);

                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }

                try {
                    bufferedReader.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                stop();

            });

            thread.start();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void shutdown(){

        if(this.cloudService.getVersion().equals(GroupVersion.BUNGEECORD) || this.cloudService.getVersion().equals(GroupVersion.WATERFALL)){
            executeCommand("end");
        } else {
            executeCommand("stop");
        }

    }

    @Override
    public void stop() {

        deleteTemporaryFiles();

        cloudService.setStatus(CloudServiceStatus.STOPPED);
        cloudService.getConsoleMessages().clear();

        CloudLauncher.getInstance().getCloudLogger().info("Trying to stop service " + cloudService.getServiceIdName() + " ...");

        CloudLauncher.getInstance().getCloudConnectionServer().getServer().sendToAllTCP(JsonLib.empty()
                .append("type", "service_unregistered")
                .append("serviceName", cloudService.getServiceIdName()).getAsJsonString());

        CloudLauncher.getInstance().getCloudServiceManager().getCloudServices().remove(cloudService);

    }

    private void deleteTemporaryFiles(){
        while(true){
            try {
                if(cloudService.isStatic()){
                    CloudLauncher.getInstance().getFileManager().deleteFiles(new File("static/" + cloudService.getServiceIdName()));
                } else {
                    CloudLauncher.getInstance().getFileManager().deleteFiles(new File("temp/" + cloudService.getServiceIdName()));
                }
                break;
            } catch (Exception ignored) {

            }
        }
    }

    @Override
    public Process getProcess() {
        return process;
    }

    @Override
    public void executeCommand(String command) {
        String cmd = command + "\n";
        try {
            if(process != null && process.getOutputStream() != null){
                process.getOutputStream().write(cmd.getBytes());
                process.getOutputStream().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> getStartArguments(){
        List<String> arguments = new ArrayList<>();
        ICloudGroup cloudGroupService = CloudLauncher.getInstance().getCloudGroupManager().getCloudGroups().stream().filter(groupService -> groupService.getName().equals(cloudService.getName())).findAny().orElse(null);

        assert cloudGroupService != null;

        arguments.add("java");
        arguments.add("-XX:-UseAdaptiveSizePolicy");
        arguments.add("-Dcom.mojang.eula.agree=true");
        arguments.add("-Djline.terminal=jline.UnsupportedTerminal");
        arguments.add("-Xms" + cloudGroupService.getMaxMemory() + "M");
        arguments.add("-Xmx" + cloudGroupService.getMaxMemory() + "M");
        arguments.add("-jar");
        arguments.add(cloudService.getVersion().getDisplay() + ".jar");

        return arguments;
    }

    private ProcessBuilder createProcessBuilder(){
        return new ProcessBuilder(getStartArguments());
    }

    @Override
    public ICloudService getCloudService() {
        return cloudService;
    }
}
