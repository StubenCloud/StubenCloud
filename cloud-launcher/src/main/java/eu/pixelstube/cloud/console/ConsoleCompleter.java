package eu.pixelstube.cloud.console;

/*

  » de.thundercloud.launcher.console

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 18.04.2021 / 18:26

 */

import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.ArrayList;
import java.util.List;

public class ConsoleCompleter implements Completer {

    private List<String> suggestions;

    @Override
    public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {

        if(suggestions == null){
            return;
        }

        if(suggestions.isEmpty()) {
            return;
        }
        candidates.addAll(getCurrentSuggestions());

    }

    public List<Candidate> getCurrentSuggestions(){
        List<Candidate> candidates = new ArrayList<>();
        for(String string : suggestions){
            candidates.add(new Candidate(string));
        }
        return candidates;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

}
