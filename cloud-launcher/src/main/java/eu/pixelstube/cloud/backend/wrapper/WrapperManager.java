package eu.pixelstube.cloud.backend.wrapper;

import eu.pixelstube.cloud.setups.WrapperSetup;
import eu.pixelstube.cloud.wrapper.IWrapper;
import eu.pixelstube.cloud.wrapper.IWrapperManager;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 22.05.2021
 * Copyright© 2021 Max H.
 **/
public class WrapperManager implements IWrapperManager {

    private JSONObject jsonObject;
    private final List<IWrapper> wrappers;

    public WrapperManager() {
        this.wrappers = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(new File("storage", "wrappers.json").toURI())), StandardCharsets.UTF_8);

            if(content.isEmpty()){
                new WrapperSetup(this);
                return;
            }

            this.jsonObject = new JSONObject(content);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void registerWrapper(IWrapper wrapper) {

        if(!wrappers.contains(wrapper)) wrappers.add(wrapper);

        if(jsonObject.getJSONObject(wrapper.getName()) != null){
            jsonObject.put(wrapper.getName(), new JSONObject().put("host", wrapper.getHost()).put("memory", wrapper.getMemory()));
            write(jsonObject);
        }

    }

    public void write(JSONObject writeObject){
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("storage", "wrappers.json")), StandardCharsets.UTF_8));

            writer.write(writeObject.toString(3));
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<IWrapper> getWrappers() {
        return wrappers;
    }
}
