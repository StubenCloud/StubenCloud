package eu.pixelstube.cloud.database;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.backend.module.reader.JsonReader;
import eu.pixelstube.cloud.database.object.DatabaseObject;
import eu.pixelstube.cloud.database.type.DatabaseType;
import eu.pixelstube.cloud.setups.SqlSetup;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 15.05.2021
 * Copyright© 2021 Max H.
 **/
public class DatabaseAdapter {

    private DatabaseObject databaseObject;

    private Connection connection;

    public DatabaseAdapter connect(){

        CloudLauncher.getInstance().getCloudLogger().info("Trying to connect to mysql...");

        if(CloudLauncher.getInstance().getFileManager().fileExist("storage", "database.json")){
            try {

                JsonReader jsonReader = null;
                try {
                    jsonReader = new JsonReader(new String(Files.readAllBytes(Paths.get(new File("storage", "database.json").toURI())), StandardCharsets.UTF_8));
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                assert jsonReader != null;

                databaseObject = new DatabaseObject((String) jsonReader.read("host"), (String) jsonReader.read("port"), (String) jsonReader.read("database"), (String) jsonReader.read("username"), (String) jsonReader.read("password"));

                connection = DriverManager.getConnection("jdbc:mysql://" + databaseObject.getHost() + ":" + databaseObject.getPort() + "/" + databaseObject.getDatabase(), databaseObject.getUsername(), databaseObject.getPassword());
                CloudLauncher.getInstance().getCloudLogger().success("Successfully connected to mysql.");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            new SqlSetup(this);
        }


        return this;
    }

    public void connect(DatabaseObject databaseObject){

        CloudLauncher.getInstance().getCloudLogger().info("Trying to connect to mysql...");

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + databaseObject.getHost() + ":" + databaseObject.getPort() + "/" + databaseObject.getDatabase(), databaseObject.getUsername(), databaseObject.getPassword());
            CloudLauncher.getInstance().getCloudLogger().success("Successfully connected to mysql.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public <T> void executeQuery(String query, ISqlFunction<ResultSet, T> consumer){
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                consumer.apply(resultSet);
            } catch (Exception ignored) {
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void executeUpdate(String query){
        try (Statement preparedStatement = connection.createStatement()){
            preparedStatement.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createTable(String table, String[] keys, DatabaseType[] databaseTypes){
        Map<String, DatabaseType> content = new HashMap<>();
        for(int i = 0; i < keys.length; i++) content.put(keys[i], databaseTypes[i]);
        StringBuilder stringBuilder = new StringBuilder("CREATE TABLE IF NOT EXISTS `").append(table).append("` (`");
        int count = 0;
        for (String key : content.keySet()) {
            stringBuilder.append(key).append("` ").append(content.get(key).append()).append(count + 1 >= content.size() ? ")" : ", `");
            count++;
        }
        executeUpdate(stringBuilder.toString());
    }

    public Connection getConnection() {
        return connection;
    }

    public DatabaseObject getDatabaseObject() {
        return databaseObject;
    }

}
