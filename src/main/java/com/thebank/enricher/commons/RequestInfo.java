package com.thebank.enricher.commons;

import java.util.UUID;

public class RequestInfo {

    private final UUID id;

    public RequestInfo() {
        this.id = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "id=" + id +
                '}';
    }
}
