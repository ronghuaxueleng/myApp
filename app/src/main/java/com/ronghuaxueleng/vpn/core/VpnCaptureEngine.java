package com.ronghuaxueleng.vpn.core;

import com.ronghuaxueleng.vpn.rule.RuleEngine;

public class VpnCaptureEngine {
    private final VpnEventDispatcher dispatcher;
    private final RuleEngine ruleEngine;
    private final TunPacketReader packetReader;
    private Thread workerThread;

    public VpnCaptureEngine(VpnEventDispatcher dispatcher, RuleEngine ruleEngine) {
        this.dispatcher = dispatcher;
        this.ruleEngine = ruleEngine;
        this.packetReader = new TunPacketReader(dispatcher);
    }

    public synchronized void start() {
        if (workerThread != null && workerThread.isAlive()) {
            return;
        }
        ruleEngine.attach(dispatcher);
        packetReader.start();
        workerThread = new Thread(packetReader, "vpn-capture-skeleton");
        workerThread.start();
    }

    public synchronized void stop() {
        packetReader.stop();
        ruleEngine.detach(dispatcher);
        workerThread = null;
    }
}
