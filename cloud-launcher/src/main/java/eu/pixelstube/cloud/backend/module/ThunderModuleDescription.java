package eu.pixelstube.cloud.backend.module;

/*

  » de.thundercloud.base.manager.modules

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 12.04.2021 / 13:36

 */

import java.util.ArrayList;
import java.util.List;

public class ThunderModuleDescription {

    private final String name, main;
    public static final List<ThunderModuleDescription> descriptions = new ArrayList<>();

    public ThunderModuleDescription(Object name, Object main) {
        this.name = String.valueOf(name);
        this.main = String.valueOf(main);

        descriptions.add(this);
    }

    public String getName() {
        return name;
    }

    public String getMain() {
        return main;
    }

}
