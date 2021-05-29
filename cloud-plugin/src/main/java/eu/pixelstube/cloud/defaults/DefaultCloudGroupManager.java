package eu.pixelstube.cloud.defaults;

import eu.pixelstube.cloud.group.ICloudGroup;
import eu.pixelstube.cloud.group.manager.ICloudGroupManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class DefaultCloudGroupManager implements ICloudGroupManager {

    private final List<ICloudGroup> cloudGroups;

    public DefaultCloudGroupManager() {
        this.cloudGroups = new ArrayList<>();
    }

    @Override
    public List<ICloudGroup> getCloudGroups() {
        return cloudGroups;
    }
}
