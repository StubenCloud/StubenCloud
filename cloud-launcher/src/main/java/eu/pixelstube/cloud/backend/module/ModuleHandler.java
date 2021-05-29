package eu.pixelstube.cloud.backend.module;

/*

  » de.thundercloud.base.manager.modules

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 07.04.2021 / 20:20

 */

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.backend.module.reader.JsonReader;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ModuleHandler {

    private final ModuleInitializer moduleInitializer;
    private static final List<ThunderModule> modules = new CopyOnWriteArrayList<>();

    public ModuleHandler() {
        moduleInitializer = new ModuleInitializer();
    }

    private File getDirectory(){
        return new File("modules");
    }

    public void registerModules(){
        File[] files = getDirectory().listFiles();
        if(files == null){
            return;
        }

        JarFile jarFile = null;

        for(File file : Arrays.stream(files).filter(Objects::nonNull).filter(this::isJarFile).collect(Collectors.toList())){
            try {

                jarFile = new JarFile(file);
                JarEntry jarEntry = jarFile.getJarEntry("thunder-module.json");

                JsonReader jsonReader;

                try (InputStream inputStream = jarFile.getInputStream(jarEntry)){
                    String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                    jsonReader = new JsonReader(content);
                }

                moduleInitializer.initModule(file.getName(), new ThunderModuleDescription(jsonReader.read("name"), jsonReader.read("main")));

            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert jarFile != null;
                    jarFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void reload(ThunderModule thunderModule){
        if(thunderModule.isReloadable()){
            CloudLauncher.getInstance().getCloudLogger().info("Reloading module " + thunderModule.getName() + " ...");

            File file = findModule(thunderModule.getName() + ".jar");
            try {
                JarFile jarFile = new JarFile(file);
                JarEntry jarEntry = jarFile.getJarEntry("thunder-module.json");

                JsonReader jsonReader;

                try (InputStream inputStream = jarFile.getInputStream(jarEntry)){
                    String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                    jsonReader = new JsonReader(content);
                }

                URLClassLoader classLoader = new URLClassLoader(new URL[]{ file.toURI().toURL()}, getClass().getClassLoader());

                Class<?> clazz = Class.forName(String.valueOf(jsonReader.read("main")), true, classLoader);

                Method method = clazz.getMethod("onReload");
                method.invoke(clazz.newInstance());

                CloudLauncher.getInstance().getCloudLogger().success("Reloaded module " + thunderModule.getName());

            } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException exception) {
                exception.printStackTrace();
            }

        }
    }

    public File findModule(String name) { return new File("modules", name); }

    public boolean isJarFile(File file){
        return file.getName().endsWith(".jar");
    }

    public List<ThunderModule> getModules() {
        return modules;
    }

}
