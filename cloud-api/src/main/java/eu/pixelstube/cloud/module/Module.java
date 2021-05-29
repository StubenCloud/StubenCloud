package eu.pixelstube.cloud.module;

/*

  » de.thundercloud.api.module

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 05.04.2021 / 16:58

 */

import eu.pixelstube.cloud.type.GroupType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Module {

    String name();
    boolean reloadable();
    GroupType[] groupType() default GroupType.PROXY;
    String version() default "";
    String[] authors() default "";

}
