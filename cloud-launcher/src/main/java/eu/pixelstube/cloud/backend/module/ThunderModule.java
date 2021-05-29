/*
 * » Created by Niklas Sch. on 12.4.2021.
 * » Class by VorGecodet.
 * » This Class/Source cannot be modified without permission.
 *
 * » Discord: VorGecodet#4033
 *
 */

package eu.pixelstube.cloud.backend.module;

/*

  » de.thundercloud.base.manager.modules

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 12.04.2021 / 13:34

 */


import eu.pixelstube.cloud.type.GroupType;

public class ThunderModule {

    public String name;
    public String version;
    public boolean reloadable;
    public String[] authors;
    public GroupType[] groupTypes;

    public ThunderModule(String name, String version, String[] authors, GroupType[] groupTypes, boolean reloadable) {
        this.name = name;
        this.version = version;
        this.authors = authors;
        this.groupTypes = groupTypes;
        this.reloadable = reloadable;
    }

    public boolean isReloadable() {
        return reloadable;
    }

    public GroupType[] getGroupTypes() {
        return groupTypes;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String[] getAuthors() {
        return authors;
    }

}
