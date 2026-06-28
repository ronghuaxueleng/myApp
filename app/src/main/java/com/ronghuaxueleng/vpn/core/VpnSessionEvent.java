package com.ronghuaxueleng.vpn.core;

public class VpnSessionEvent {
    public static final String TYPE_UPSERT = "upsert";
    public static final String TYPE_CLEAR = "clear";

    private final String type;
    private final String sessionId;
    private final HttpMessage message;

    private VpnSessionEvent(String type, String sessionId, HttpMessage message) {
        this.type = type;
        this.sessionId = sessionId;
        this.message = message;
    }

    public static VpnSessionEvent upsert(String sessionId, HttpMessage message) {
        return new VpnSessionEvent(TYPE_UPSERT, sessionId, message);
    }

    public static VpnSessionEvent clear() {
        return new VpnSessionEvent(TYPE_CLEAR, null, null);
    }

    public String getType() {
        return type;
    }

    public String getSessionId() {
        return sessionId;
    }

    public HttpMessage getMessage() {
        return message;
    }
}
