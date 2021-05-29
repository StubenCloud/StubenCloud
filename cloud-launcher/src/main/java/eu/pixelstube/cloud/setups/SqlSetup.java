package eu.pixelstube.cloud.setups;

import eu.pixelstube.cloud.console.setup.abstracts.SetupEnd;
import eu.pixelstube.cloud.console.setup.abstracts.SetupInput;
import eu.pixelstube.cloud.console.setup.interfaces.ISetup;
import eu.pixelstube.cloud.database.DatabaseAdapter;
import eu.pixelstube.cloud.database.object.DatabaseObject;
import eu.pixelstube.cloud.jsonlib.JsonLib;

import java.io.File;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
public class SqlSetup extends ISetup {

    private String host, port, database, username, password;

    public SqlSetup(DatabaseAdapter databaseAdapter) {

        setSetupEnd(new SetupEnd() {
            @Override
            public void handle() {

                JsonLib jsonLib = JsonLib.empty();

                jsonLib.append("host", host);
                jsonLib.append("port", port);
                jsonLib.append("database", database);
                jsonLib.append("username", username);
                jsonLib.append("password", password);

                jsonLib.saveAsFile(new File("storage", "database.json"));

                databaseAdapter.connect(new DatabaseObject(host, port, database, username, password));

            }
        });

        setSetupInputs(new SetupInput("Please provide the sql host") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                host = input;
                return true;
            }
        }, new SetupInput("Please provide the sql port") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                port = input;
                return port.matches("[0-9]+");
            }
        }, new SetupInput("Please provide the sql database") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                database = input;
                return true;
            }
        }, new SetupInput("Please provide the sql username") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                username = input;
                return true;
            }
        }, new SetupInput("Please provide the sql password") {
            @Override
            public List<String> getSuggestions() {
                return null;
            }

            @Override
            public boolean handle(String input) {
                password = input;
                return true;
            }
        });

    }
}
