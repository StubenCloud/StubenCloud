package eu.pixelstube.cloud.database.type;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 15.05.2021
 * Copyright© 2021 Max H.
 **/
public enum DatabaseType {

    VARCHAR("varchar", 255),
    INT("int", 0),
    LONGBLOB("LONGBLOB", 0);

    String name;
    int length;

    DatabaseType(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String append(){
        return name + (length == 0 ? "" : "(" + length + ")");
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

}
