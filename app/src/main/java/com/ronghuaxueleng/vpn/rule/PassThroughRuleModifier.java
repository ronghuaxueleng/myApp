package com.ronghuaxueleng.vpn.rule;

import com.ronghuaxueleng.vpn.VpnRule;
import com.ronghuaxueleng.vpn.core.HttpMessage;

public class PassThroughRuleModifier implements VpnRuleModifier {
    @Override
    public HttpMessage apply(VpnRule rule, HttpMessage message) {
        return message;
    }
}
