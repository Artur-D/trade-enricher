package com.thebank.enricher.commons;

import com.thebank.enricher.commons.RequestInfo;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RequestInfoRegistry {

    private static final ThreadLocal<RequestInfo> threadLocal = new ThreadLocal<>();
    private static final Map<UUID, RequestInfo> requestIdToRequestInfo = new ConcurrentHashMap<>();

    public static RequestInfo getRequestInfo() {
        return threadLocal.get();
    }
}
