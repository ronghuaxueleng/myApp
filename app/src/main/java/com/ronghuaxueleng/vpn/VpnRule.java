package com.ronghuaxueleng.vpn;

public class VpnRule {
    private final String name;
    private final String match;
    private final String action;
    private final boolean enabled;

    public VpnRule(String name, String match, String action, boolean enabled) {
        this.name = name;
        this.match = match;
        this.action = action;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public String getMatch() {
        return match;
    }

    public String getAction() {
        return action;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
