package eu.pixelstube.cloud.command;

/*

  » de.thundercloud.api.command

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 31.03.2021 / 18:32

 */

import eu.pixelstube.cloud.console.ICommandSender;
import org.jline.reader.Candidate;

import java.util.List;

public interface ICommandHandler {

    void handle(ICommandSender iCommandSender, String[] args);

    List<Candidate> getSuggestions();

}
