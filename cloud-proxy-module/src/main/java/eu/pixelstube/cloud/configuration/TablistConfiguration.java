package eu.pixelstube.cloud.configuration;

import java.util.List;

/**
 * This file was created by Max H. (Haizoooon)
 * Date: 25.05.2021
 * Copyright© 2021 Max H.
 **/
public class TablistConfiguration {

    private final List<String> header;
    private final List<String> footer;

    public TablistConfiguration(List<String> header, List<String> footer) {
        this.header = header;
        this.footer = footer;
    }

    public List<String> getHeader() {
        return header;
    }

    public List<String> getFooter() {
        return footer;
    }
}
