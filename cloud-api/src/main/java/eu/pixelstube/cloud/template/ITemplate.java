package eu.pixelstube.cloud.template;

import eu.pixelstube.cloud.template.type.TemplateType;

import java.util.UUID;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 26.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ITemplate {

    /**
     * Returns the uuid from the template
     */
    UUID getUUID();

    /**
     * Returns the template name
     */
    String getName();

    /**
     * Returns the template type. e.g. DYNAMIC
     */
    TemplateType getType();

}
