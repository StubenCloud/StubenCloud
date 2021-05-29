package eu.pixelstube.cloud.backend.module.reader;

import org.json.JSONObject;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class JsonReader {

    private final JSONObject jsonObject;

    public JsonReader(String content) {
        this.jsonObject = new JSONObject(content);
    }

    public Object read(String var1){
        return jsonObject.get(var1);
    }
}

