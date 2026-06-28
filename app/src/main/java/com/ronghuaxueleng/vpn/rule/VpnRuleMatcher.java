package com.ronghuaxueleng.vpn.rule;

import com.ronghuaxueleng.vpn.VpnRule;
import com.ronghuaxueleng.vpn.core.HttpMessage;

public interface VpnRuleMatcher {
    boolean matches(VpnRule rule, HttpMessage message);
}
