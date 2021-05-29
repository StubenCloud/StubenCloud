package eu.pixelstube.cloud.console.setup.abstracts;

/*

  » de.thundercloud.launcher.console.setup.abstracts

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 31.03.2021 / 11:49

 */

import java.util.List;

public abstract class SetupInput {

    private final String question;

    public SetupInput(String question) {
        this.question = question;
    }

    public abstract List<String> getSuggestions();

    public abstract boolean handle(String input);

    public String getQuestion() {
        return question;
    }

}
