package com.pinkladydev.web.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameNotificationRequest {

    private final String webId;

    public GameNotificationRequest(@JsonProperty("webId") String webId) {
        this.webId = webId;
    }

    public String getWebId() {
        return webId;
    }
}
