package com.pinkladydev.darts.web.models;

import com.pinkladydev.darts.game.DartResponseType;

public class DartResponse {

    private final DartResponseType dartResponseType;

    public DartResponse(DartResponseType dartResponseType) {
        this.dartResponseType = dartResponseType;
    }

    public DartResponseType getDartResponseType() {
        return dartResponseType;
    }
}
