package eu.pixelstube.cloud.property;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 18.05.2021
 * Copyright© 2021 Max H.
 **/
public interface IProperty<T> {

    /**
     * Returns the value
     * @return
     */
    T getValue();

    /**
     * Returns the value as string
     * @return
     */
    String getValueAsString();

}
