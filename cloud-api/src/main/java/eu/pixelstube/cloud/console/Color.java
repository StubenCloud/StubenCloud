package eu.pixelstube.cloud.console;

/*

  » de.thundercloud.api.console

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 18.04.2021 / 18:27

 */

import org.fusesource.jansi.Ansi;

public enum Color {

    RESET("r", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.DEFAULT).boldOff().toString()),

    BLACK("0", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString()),
    RED("c", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString()),
    GREEN("a", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString()),
    YELLOW("e", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString()),
    YELLOW_BOLD("el", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString()),
    BLUE("9", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString()),
    MAGENTA("5", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString()),
    CYAN("b", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString()),
    WHITE("f", Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString());

    private final String index;
    private final String color;

    Color(String index, String color) {
        this.index = index;
        this.color = color;
    }

    public static String toColoredString(String message){
        String text = message;
        for(Color color : values()){
            text = text.replace("§" + "" + color.getIndex(), color.getColor());
            text = text.replace("&" + "" + color.getIndex(), color.getColor());
        }
        return text;
    }

    public String getIndex() {
        return index;
    }

    public String getColor() {
        return color;
    }

}
