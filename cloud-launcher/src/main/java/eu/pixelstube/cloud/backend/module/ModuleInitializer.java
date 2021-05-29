package eu.pixelstube.cloud.backend.module;

/*

  » de.thundercloud.base.manager.modules

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 12.04.2021 / 13:35

 */

import eu.pixelstube.cloud.CloudLauncher;
import eu.pixelstube.cloud.logger.CloudLogger;
import eu.pixelstube.cloud.module.Module;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.regex.Pattern;

public class ModuleInitializer {

    public static final Pattern ID_PATTERN = Pattern.compile("[a-z][a-z0-9-_]{0,63}");

    public void initModule(String file, ThunderModuleDescription thunderModuleDescription) {
        try {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{ new ModuleHandler().findModule(file).toURI().toURL()}, getClass().getClassLoader());

            Class<?> clazz = Class.forName(thunderModuleDescription.getMain(), true, classLoader);

            if (clazz.isEnum() || clazz.isInterface()) {
                return;
            }

            Object instance = clazz.newInstance();

            if (clazz.getAnnotation(Module.class) == null) {
                return;
            }

            Module module = clazz.getAnnotation(Module.class);

            if(ID_PATTERN.matcher(module.name()).matches()){

                String name = module.name();
                String version = module.version();
                String[] authors = module.authors();

                CloudLauncher.getInstance().getCloudLogger().info("Loaded module " + name + " " + version + " by " + Arrays.toString(authors));

                CloudLauncher.getInstance().getModuleHandler().getModules().add(new ThunderModule(name, version, authors, module.groupType(), module.reloadable()));

            }

            Method method = clazz.getMethod("onInitialization", CloudLogger.class);
            method.invoke(instance, CloudLauncher.getInstance().getCloudLogger());

        } catch (MalformedURLException | IllegalAccessException | InstantiationException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException  e) {
            e.printStackTrace();
        }
    }


}
