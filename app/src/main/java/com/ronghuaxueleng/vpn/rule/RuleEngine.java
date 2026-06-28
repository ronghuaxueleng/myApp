package com.ronghuaxueleng.vpn.rule;

import com.ronghuaxueleng.vpn.VpnRule;
import com.ronghuaxueleng.vpn.VpnRuleRepository;
import com.ronghuaxueleng.vpn.core.HttpMessage;
import com.ronghuaxueleng.vpn.core.VpnEventDispatcher;
import com.ronghuaxueleng.vpn.core.VpnEventListener;
import com.ronghuaxueleng.vpn.core.VpnSessionEvent;

import java.util.List;

public class RuleEngine implements VpnEventListener {
    private final VpnRuleMatcher matcher;
    private final VpnRuleModifier modifier;

    public RuleEngine(VpnRuleMatcher matcher, VpnRuleModifier modifier) {
        this.matcher = matcher;
        this.modifier = modifier;
    }

    public void attach(VpnEventDispatcher dispatcher) {
        dispatcher.register(this);
    }

    public void detach(VpnEventDispatcher dispatcher) {
        dispatcher.unregister(this);
    }

    @Override
    public void onSessionEvent(VpnSessionEvent event) {
        if (!VpnSessionEvent.TYPE_UPSERT.equals(event.getType()) || event.getMessage() == null) {
            return;
        }
        HttpMessage current = event.getMessage();
        List<VpnRule> rules = VpnRuleRepository.getInstance().getRules();
        for (VpnRule rule : rules) {
            if (matcher.matches(rule, current)) {
                current = modifier.apply(rule, current);
            }
        }
    }
}
