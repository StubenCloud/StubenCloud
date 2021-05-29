package eu.pixelstube.cloud.database;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 21.05.2021
 * Copyright© 2021 Max H.
 **/
@FunctionalInterface
public interface ISqlFunction<I, O> {

    O apply(I i);

}
