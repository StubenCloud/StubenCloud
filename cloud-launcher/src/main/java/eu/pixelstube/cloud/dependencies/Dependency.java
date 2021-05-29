package eu.pixelstube.cloud.dependencies;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 20.05.2021
 * Copyright© 2021 Max H.
 **/
public class Dependency {

    private final String groupId, artifactId, version;

    public Dependency(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

}
