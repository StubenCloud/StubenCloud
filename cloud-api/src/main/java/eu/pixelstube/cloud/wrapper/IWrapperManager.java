package eu.pixelstube.cloud.wrapper;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 22.05.2021
 * Copyright© 2021 Max H.
 **/
public interface IWrapperManager {

    /**
     * Register a new wrapper
     * @param wrapper
     */
    void registerWrapper(IWrapper wrapper);

    /**
     * Returns all registered wrappers
     * @return
     */
    List<IWrapper> getWrappers();

}
