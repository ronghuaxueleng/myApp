package com.ronghuaxueleng.vpn.rule;

import com.ronghuaxueleng.vpn.VpnRule;
import com.ronghuaxueleng.vpn.core.HttpMessage;

public class DefaultRuleMatcher implements VpnRuleMatcher {
    @Override
    public boolean matches(VpnRule rule, HttpMessage message) {
        if (!rule.isEnabled()) {
            return false;
        }
        String match = rule.getMatch();
        return match.contains(message.getHost()) || match.contains(message.getMethod()) || match.contains(message.getPath());
    }
}
