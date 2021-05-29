package eu.pixelstube.cloud.console;

/*

  » de.thundercloud.api.console

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 18.04.2021 / 18:28

 */

public enum LogType {

    DEBUG("DEBUG", Color.BLUE),
    CONSOLE("INFO", Color.MAGENTA),
    ERROR("ERROR", Color.RED),
    WARNING("WARN", Color.YELLOW),
    SETUP("SETUP", Color.CYAN),
    SUCCESS("SUCCESS", Color.GREEN);

    private String display;
    private Color colors;

    LogType(String display, Color colors) {
        this.display = display;
        this.colors = colors;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Color getColor() {
        return colors;
    }

    public void setColors(Color colors) {
        this.colors = colors;
    }

}
