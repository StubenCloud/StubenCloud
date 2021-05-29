package eu.pixelstube.cloud.logger;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 29.05.2021
 * Copyright© 2021 Max H.
 **/
public interface ICloudLogger {

    void info(String message);

    void severe(String message);

    void warn(String message);

    void success(String message);

}
