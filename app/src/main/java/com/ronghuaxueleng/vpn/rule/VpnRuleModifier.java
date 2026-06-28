package com.ronghuaxueleng.vpn.rule;

import com.ronghuaxueleng.vpn.VpnRule;
import com.ronghuaxueleng.vpn.core.HttpMessage;

public interface VpnRuleModifier {
    HttpMessage apply(VpnRule rule, HttpMessage message);
}
