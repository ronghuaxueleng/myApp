package com.ronghuaxueleng.vpn.core;

public class TunPacketReader implements Runnable {
    private final VpnEventDispatcher dispatcher;
    private volatile boolean running;

    public TunPacketReader(VpnEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        if (!running) {
            return;
        }
        emitSkeletonTraffic();
    }

    private void emitSkeletonTraffic() {
        dispatcher.dispatch(VpnSessionEvent.clear());
        dispatcher.dispatch(VpnSessionEvent.upsert("pipe-1", new HttpMessage(
                "HTTP/1.1",
                "GET",
                "api.example.com",
                "/v1/projects",
                "Accept: application/json\nUser-Agent: VpnSkeleton/1.0",
                "",
                "Content-Type: application/json",
                "{\"items\":[{\"id\":\"p1\",\"name\":\"Demo Project\"}]}",
                "200 OK",
                "pipeline sample · 46 ms"
        )));
        dispatcher.dispatch(VpnSessionEvent.upsert("pipe-2", new HttpMessage(
                "HTTP/2",
                "POST",
                "auth.example.com",
                "/v1/login",
                "Content-Type: application/json\nX-Debug-Trace: skeleton",
                "{\"mobile\":\"159****4680\",\"code\":\"123456\"}",
                "Content-Type: application/json",
                "{\"message\":\"unauthorized\"}",
                "401",
                "rule ready · request editable"
        )));
    }
}
