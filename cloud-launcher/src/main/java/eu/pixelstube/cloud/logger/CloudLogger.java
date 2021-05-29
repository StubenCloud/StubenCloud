package eu.pixelstube.cloud.logger;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.console.Color;
import eu.pixelstube.cloud.console.LogType;
import org.jline.reader.LineReader;
import org.jline.utils.InfoCmp;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 15.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudLogger extends Logger {

    private final CopyOnWriteArrayList<String> cachedMessages = new CopyOnWriteArrayList<>();

    private final File logDir = new File("logs/");

    public CloudLogger() {
        super("StubenCloud", null);

        setLevel(Level.ALL);
        setUseParentHandlers(false);

        logDir.mkdirs();

        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] [%4$-7s] %5$s %n");

        File file = new File("logs/");
        SimpleFormatter simpleFormatter = new SimpleFormatter();

        try {

            FileHandler fileHandler = new FileHandler(file.getCanonicalPath() + "/stuben-log", 5242880, 100, false);
            fileHandler.setEncoding(StandardCharsets.UTF_8.name());
            fileHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(simpleFormatter);

            addHandler(fileHandler);

        } catch (IOException e) {
            e.printStackTrace();
        }

        deleteOldLogs();

    }

    public void write(String prefix, String message){
        Date date = new Date();
        String format = Color.toColoredString("§r[" + new SimpleDateFormat("HH:mm:ss").format(date) + " | " + Color.CYAN.getColor() + prefix + Color.RESET.getColor() + "]" + Color.RESET.getColor() + " §r»§r " + message);

        LineReader lineReader = CloudLauncher.getInstance().getConsoleManager().getLineReader();
        lineReader.getTerminal().puts(InfoCmp.Capability.carriage_return);
        lineReader.getTerminal().writer().println(format);
        lineReader.getTerminal().flush();

        if(lineReader.isReading()){
            lineReader.callWidget(LineReader.REDRAW_LINE);
            lineReader.callWidget(LineReader.REDISPLAY);
        }

        cachedMessages.add("[" + new SimpleDateFormat("HH:mm:ss").format(date) + "] " + prefix + ": " + message);
    }

    private void write(LogType logType, String message){
        Date date = new Date();
        String format = Color.toColoredString("§r[" + new SimpleDateFormat("HH:mm:ss").format(date) + " | " + logType.getColor().getColor() + logType.getDisplay()+ Color.RESET.getColor() + "]" + Color.RESET.getColor() + " §r»§r " + message);

        LineReader lineReader = CloudLauncher.getInstance().getConsoleManager().getLineReader();
        lineReader.getTerminal().puts(InfoCmp.Capability.carriage_return);
        lineReader.getTerminal().writer().println(format);
        lineReader.getTerminal().flush();

        if(lineReader.isReading()){
            lineReader.callWidget(LineReader.REDRAW_LINE);
            lineReader.callWidget(LineReader.REDISPLAY);
        }

        cachedMessages.add("[" + new SimpleDateFormat("HH:mm:ss").format(date) + "] " + logType.getDisplay() + ": " + message);
    }

    @Override
    public void info(String msg) {
        super.info(msg);
        write(LogType.CONSOLE, msg);
    }

    @Override
    public void warning(String msg) {
        super.warning(msg);
        write(LogType.WARNING, msg);
    }

    @Override
    public void severe(String msg) {
        super.severe(msg);
        write(LogType.ERROR, msg);
    }

    public void setup(String msg){
        super.info(msg);
        write(LogType.SETUP, msg);
    }

    public void success(String message){
        super.info(message);
        write(LogType.SUCCESS, message);
    }

    public void debug(String msg){
        super.info(msg);
        write(LogType.DEBUG, msg);
    }

    public void deleteOldLogs(){
        File[] files = logDir.listFiles();
        if(files == null){
            return;
        }
        Arrays.stream(files).forEach(file -> {
            if (isOlder(file)) {
                file.delete();
            }
        });
    }

    private boolean isOlder(File file){
        long millis = TimeUnit.DAYS.toMillis(10);
        return (System.currentTimeMillis() - file.lastModified()) > millis;
    }

    public void clear(){
        System.out.print("\u001b[H\u001b[2J");
        System.out.flush();
    }

    public CopyOnWriteArrayList<String> getCachedMessages() {
        return cachedMessages;
    }

}
