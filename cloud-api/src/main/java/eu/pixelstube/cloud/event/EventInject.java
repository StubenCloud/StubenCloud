package eu.pixelstube.cloud.event;

/*

  » de.thundercloud.api.event

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 21.04.2021 / 09:17

 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventInject {

    byte priority() default EventPriority.NORMAL;

}
