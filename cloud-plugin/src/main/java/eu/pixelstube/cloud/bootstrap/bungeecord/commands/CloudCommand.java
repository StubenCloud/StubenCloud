package eu.pixelstube.cloud.bootstrap.bungeecord.commands;

import eu.pixelstube.cloud.CloudAPI;
import eu.pixelstube.cloud.CloudPlugin;
import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.service.ICloudService;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudCommand extends Command {

    public CloudCommand() {
        super("cloud");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {

        if(commandSender instanceof ProxiedPlayer){

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;

            if(args.length == 0){
                proxiedPlayer.sendMessage("§8§m---------------");
                proxiedPlayer.sendMessage("");
                proxiedPlayer.sendMessage("§7/cloud service");
                proxiedPlayer.sendMessage("§7/cloud list");
                proxiedPlayer.sendMessage("§7/cloud groups");
                proxiedPlayer.sendMessage("");
                proxiedPlayer.sendMessage("§8§m---------------");
                return;
            }

            if(args.length == 1){
                if(args[0].equalsIgnoreCase("list")){

                    proxiedPlayer.sendMessage("§8§m---------------");
                    proxiedPlayer.sendMessage("");
                    proxiedPlayer.sendMessage("§b" + "MainWrapper");
                    proxiedPlayer.sendMessage("");
                    for (ICloudGroup cloudGroup : CloudAPI.getInstance().getCloudGroupManager().getCloudGroups()) {

                        proxiedPlayer.sendMessage("§8» §e" + cloudGroup.getName());
                        for (ICloudService cloudService : CloudAPI.getInstance().getCloudServiceManager().getCloudServices()) {
                            if(cloudService.getGroupName().equalsIgnoreCase(cloudGroup.getName())){
                                proxiedPlayer.sendMessage("§8- §6" + cloudService.getServiceIdName() + " §8(§e" + cloudService.getServiceStatus() + "§8)");
                            }
                        }
                        proxiedPlayer.sendMessage("");

                    }
                    proxiedPlayer.sendMessage("");
                    proxiedPlayer.sendMessage("§8§m---------------");

                } else if(args[0].equalsIgnoreCase("service")){

                    ICloudService cloudService = CloudPlugin.getInstance().thisService();

                    proxiedPlayer.sendMessage("§8§m---------------");
                    proxiedPlayer.sendMessage("");
                    proxiedPlayer.sendMessage("§b" + cloudService.getServiceIdName());
                    proxiedPlayer.sendMessage("§7" + cloudService.getPort());
                    proxiedPlayer.sendMessage("§7" + cloudService.getVersion().getDisplay());
                    proxiedPlayer.sendMessage("§7" + cloudService.getCloudGroup().getGroupType().getName());
                    proxiedPlayer.sendMessage("");
                    proxiedPlayer.sendMessage("§8§m---------------");

                }
            }

        }

    }

}
