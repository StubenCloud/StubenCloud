package eu.pixelstube.cloud;

import eu.pixelstube.cloud.backend.external.handler.EventBus;
import eu.pixelstube.cloud.backend.file.FileManager;
import eu.pixelstube.cloud.backend.module.ModuleHandler;
import eu.pixelstube.cloud.backend.module.reader.JsonReader;
import eu.pixelstube.cloud.backend.wrapper.WrapperManager;
import eu.pixelstube.cloud.commands.*;
import eu.pixelstube.cloud.connection.CloudConnectionServer;
import eu.pixelstube.cloud.console.ConsoleManager;
import eu.pixelstube.cloud.console.command.CommandManager;
import eu.pixelstube.cloud.console.sender.ConsoleSender;
import eu.pixelstube.cloud.database.DatabaseAdapter;
import eu.pixelstube.cloud.database.object.DatabaseObject;
import eu.pixelstube.cloud.database.type.DatabaseType;
import eu.pixelstube.cloud.dependencies.DependencyLoader;
import eu.pixelstube.cloud.event.IEvent;
import eu.pixelstube.cloud.group.CloudGroupManager;
import eu.pixelstube.cloud.logger.CloudLogger;
import eu.pixelstube.cloud.service.CloudServiceManager;
import eu.pixelstube.cloud.template.TemplateManager;
import eu.pixelstube.cloud.wrapper.IWrapperManager;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 15.05.2021
 * Copyright© 2021 Max H.
 **/
public class CloudLauncher {

    private static CloudLauncher instance;

    private final CommandManager commandManager;
    private final ConsoleManager consoleManager;
    private final CloudLogger cloudLogger;
    private final ConsoleSender consoleSender;
    private final DatabaseAdapter databaseAdapter;
    private final DependencyLoader dependencyLoader;

    private final CloudGroupManager cloudGroupManager;
    private final CloudConnectionServer cloudConnectionServer;
    private final CloudServiceManager cloudServiceManager;
    private final TemplateManager templateManager;

    private final FileManager fileManager;
    private final IWrapperManager wrapperManager;
    private final EventBus eventBus;
    private final ModuleHandler moduleHandler;

    public static void main(String[] args) {
        new CloudLauncher();
    }

    public CloudLauncher() {
        instance = this;

        fileManager = new FileManager();

        fileManager.createFolders("storage/jars", "modules", "dependencies", "templates", "static", "temp");
        fileManager.createFile("storage", "wrappers.json");

        consoleManager = new ConsoleManager();
        cloudLogger = new CloudLogger();
        consoleSender = new ConsoleSender();
        wrapperManager = new WrapperManager();

        dependencyLoader = new DependencyLoader();
        dependencyLoader.loadDependency("com.google.collections", "google-collections", "1.0");
        dependencyLoader.loadDependency("com.google.code.gson", "gson", "2.2.4");
        dependencyLoader.loadDependency("org.jline", "jline-reader", "3.19.0");
        dependencyLoader.loadDependency("org.jline", "jline-terminal", "3.19.0");
        dependencyLoader.loadDependency("org.fusesource.jansi", "jansi", "2.3.2");
        dependencyLoader.loadDependency("org.json", "json", "20201115");
        dependencyLoader.loadDependency("mysql", "mysql-connector-java", "8.0.23");
        dependencyLoader.loadDependency("commons-io", "commons-io", "2.8.0");
        dependencyLoader.loadDependency("org.reflections", "reflections", "0.9.12");
        dependencyLoader.loadDependency("org.objenesis", "objenesis", "3.2");

        dependencyLoader.getDependencies().forEach(dependency -> {
            getCloudLogger().success("Loaded dependency " + dependency.getGroupId() + " " + dependency.getArtifactId() + " " + dependency.getVersion());
        });

        Arrays.stream(Objects.requireNonNull(new File("dependencies").listFiles())).forEach(dependencyLoader::addDependency);

        getCloudLogger().clear();
        getCloudLogger().info("Trying to start cloud...");

        commandManager = new CommandManager();

        JsonReader jsonReader = null;
        try {
            jsonReader = new JsonReader(new String(Files.readAllBytes(Paths.get(new File("storage", "database.json").toURI())), StandardCharsets.UTF_8));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        assert jsonReader != null;

        databaseAdapter = new DatabaseAdapter(new DatabaseObject((String) jsonReader.read("host"), (String) jsonReader.read("port"), (String) jsonReader.read("database"), (String) jsonReader.read("username"), (String) jsonReader.read("password"))).connect();

        databaseAdapter.createTable("cloud_players", new String[]{"name", "uniqueId", "info"}, new DatabaseType[]{DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.LONGBLOB});
        databaseAdapter.createTable("cloud_groups",
                new String[]{"name", "maxServices", "minServices", "maxMemory", "percentage", "groupVersion", "groupType", "maintenance", "maxPlayers", "template"},
                new DatabaseType[]{DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR});

        databaseAdapter.createTable("cloud_templates", new String[]{"uniqueId", "name", "type"}, new DatabaseType[]{DatabaseType.VARCHAR, DatabaseType.VARCHAR, DatabaseType.VARCHAR});

        commandManager.register(
                CreateCommand.class,
                ScreenCommand.class,
                StopCommand.class,
                ShutdownCommand.class,
                InfoCommand.class,
                HelpCommand.class,
                ReloadCommand.class
        );

        CloudLauncher.getInstance().getCloudLogger().info("Loaded " + commandManager.getCommandHandlers().size() + " commands!");

        this.cloudConnectionServer = new CloudConnectionServer();

        moduleHandler = new ModuleHandler();
        moduleHandler.registerModules();

        templateManager = new TemplateManager();

        cloudGroupManager = new CloudGroupManager();
        cloudGroupManager.fetchGroups();

        getCloudLogger().info("Loaded " + cloudGroupManager.getCloudGroups().size() + " groups");
        cloudGroupManager.getCloudGroups().forEach(iCloudGroup -> {
            getCloudLogger().info("- " + iCloudGroup.getName());
        });

        cloudServiceManager = new CloudServiceManager();
        cloudServiceManager.fetchServices();


        eventBus = new EventBus();

    }

    public <T extends IEvent> void callEvent(T event) {

        eventBus.post(event);
        event.postCall();

    }

    public TemplateManager getTemplateManager() {
        return templateManager;
    }

    public ModuleHandler getModuleHandler() {
        return moduleHandler;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public CloudServiceManager getCloudServiceManager() {
        return cloudServiceManager;
    }

    public IWrapperManager getWrapperManager() {
        return wrapperManager;
    }

    public CloudConnectionServer getCloudConnectionServer() {
        return cloudConnectionServer;
    }

    public CloudGroupManager getCloudGroupManager() {
        return cloudGroupManager;
    }

    public DependencyLoader getDependencyLoader() {
        return dependencyLoader;
    }

    public DatabaseAdapter getDatabaseAdapter() {
        return databaseAdapter;
    }

    public ConsoleSender getConsoleSender() {
        return consoleSender;
    }

    public CloudLogger getCloudLogger() {
        return cloudLogger;
    }

    public ConsoleManager getConsoleManager() {
        return consoleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public static CloudLauncher getInstance() {
        return instance;
    }
}
