package eu.pixelstube.cloud.console.sender;

/*

  » de.thundercloud.launcher.console.sender

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 18.04.2021 / 21:26

 */

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.console.ICommandSender;

public class ConsoleSender implements ICommandSender {

    @Override
    public void sendMessage(String message) {
        CloudLauncher.getInstance().getCloudLogger().info(message);
    }

}
