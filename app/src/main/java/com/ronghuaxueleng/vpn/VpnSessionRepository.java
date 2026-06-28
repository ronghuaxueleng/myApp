package com.ronghuaxueleng.vpn;

import com.ronghuaxueleng.vpn.core.HttpMessage;
import com.ronghuaxueleng.vpn.core.VpnEventListener;
import com.ronghuaxueleng.vpn.core.VpnSessionEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VpnSessionRepository implements VpnEventListener {
    private static final VpnSessionRepository INSTANCE = new VpnSessionRepository();

    private final List<VpnSession> sessions = new ArrayList<>();
    private boolean running;

    private VpnSessionRepository() {
        seedDemoSessions();
    }

    public static VpnSessionRepository getInstance() {
        return INSTANCE;
    }

    public synchronized List<VpnSession> getSessions() {
        return Collections.unmodifiableList(new ArrayList<>(sessions));
    }

    public synchronized VpnSession findById(String sessionId) {
        for (VpnSession session : sessions) {
            if (session.getId().equals(sessionId)) {
                return session;
            }
        }
        return null;
    }

    public synchronized boolean isRunning() {
        return running;
    }

    public synchronized void setRunning(boolean running) {
        this.running = running;
    }

    public synchronized void resetToDemoData() {
        sessions.clear();
        seedDemoSessions();
    }

    @Override
    public synchronized void onSessionEvent(VpnSessionEvent event) {
        if (VpnSessionEvent.TYPE_CLEAR.equals(event.getType())) {
            sessions.clear();
            return;
        }
        if (!VpnSessionEvent.TYPE_UPSERT.equals(event.getType()) || event.getMessage() == null) {
            return;
        }
        upsert(event.getSessionId(), event.getMessage());
    }

    private void upsert(String sessionId, HttpMessage message) {
        VpnSession session = new VpnSession(
                sessionId,
                message.getMethod(),
                message.getProtocol(),
                message.getHost(),
                message.getPath(),
                message.getStatus(),
                message.getSummary(),
                message.getRequestHeaders(),
                message.getRequestBody(),
                message.getResponseHeaders(),
                message.getResponseBody());
        for (int i = 0; i < sessions.size(); i++) {
            if (sessions.get(i).getId().equals(sessionId)) {
                sessions.set(i, session);
                return;
            }
        }
        sessions.add(0, session);
    }

    private void seedDemoSessions() {
        sessions.add(new VpnSession(
                "s1", "GET", "HTTP/1.1", "api.example.com", "/v1/projects", "200 OK", "46 ms · 1.2 KB",
                "Accept: application/json\nUser-Agent: ReqableSkeleton/1.0",
                "",
                "Content-Type: application/json",
                "{\"items\":[{\"id\":\"p1\",\"name\":\"Demo Project\"}]}"));
        sessions.add(new VpnSession(
                "s2", "POST", "HTTP/2", "auth.example.com", "/v1/login", "401", "82 ms · body editable",
                "Content-Type: application/json\nX-Debug-Trace: skeleton",
                "{\"mobile\":\"159****4680\",\"code\":\"123456\"}",
                "Content-Type: application/json",
                "{\"message\":\"unauthorized\"}"));
        sessions.add(new VpnSession(
                "s3", "CONNECT", "TLS", "secure.example.com", ":443", "TLS", "等待 HTTPS MITM 模块接入",
                "SNI: secure.example.com",
                "",
                "Handshake: pending",
                ""));
    }
}
