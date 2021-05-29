package eu.pixelstube.cloud.event;

/*

  » de.thundercloud.api.event

  » Methode/Class coded by Haizoooon#6495
  » This Class/Source cannot be modified without permission.
  » Please refrain from recoding
  » Questions may be asked in Discord

  » Package coded at: 21.04.2021 / 08:14

 */

import java.util.List;

public interface IEventHandler {


    /**
     * Register a event
     * @param event
     */
    void registerEvent(IEvent event);

    /**
     * Call a event
     * @param event
     */
    void call(IEvent event);

    /**
     * Get all events
     * @return
     */
    List<IEvent> getEvents();

}
