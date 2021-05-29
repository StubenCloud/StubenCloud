package eu.pixelstube.cloud.template;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 26.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ITemplateManager {

    /**
     * Returns a cached template
     * @param name
     */
    ITemplate getCachedTemplate(String name);

    /**
     * Returns all templates
     */
    List<ITemplate> getTemplates();

}
