package com.ronghuaxueleng.vpn.core;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class VpnEventDispatcher {
    private static final VpnEventDispatcher INSTANCE = new VpnEventDispatcher();

    private final List<VpnEventListener> listeners = new CopyOnWriteArrayList<>();

    public static VpnEventDispatcher getInstance() {
        return INSTANCE;
    }

    public void register(VpnEventListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void unregister(VpnEventListener listener) {
        listeners.remove(listener);
    }

    public void dispatch(VpnSessionEvent event) {
        for (VpnEventListener listener : listeners) {
            listener.onSessionEvent(event);
        }
    }
}
