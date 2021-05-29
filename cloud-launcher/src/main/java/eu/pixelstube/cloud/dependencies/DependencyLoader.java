package eu.pixelstube.cloud.dependencies;

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.url.UrlDownloader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 20.05.2021
 * Copyright© 2021 Max H.
 **/
public class DependencyLoader {

    private final List<Dependency> dependencies;

    public DependencyLoader() {
        this.dependencies = new ArrayList<>();
    }

    private final URLClassLoader classLoader = ((URLClassLoader) ClassLoader.getSystemClassLoader());

    public void loadDependency(String groupId, String artifactId, String version){
        CloudLauncher.getInstance().getCloudLogger().info("Loading dependency " + artifactId + "...");
        dependencies.add(new Dependency(groupId, artifactId, version));
        if(new File("dependencies", artifactId + "-" + version + ".jar").exists()){
           return;
        }
        new UrlDownloader("https://repo1.maven.org/maven2/" + groupId.replace(".", "/") + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".jar", new File("dependencies", artifactId + "-" + version + ".jar")).download();
    }

    public void addDependency(File file){
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);

            method.invoke(classLoader, file.toURI().toURL());
        } catch (MalformedURLException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }
}
