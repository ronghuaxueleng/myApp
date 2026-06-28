package com.ronghuaxueleng.vpn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VpnRuleRepository {
    private static final VpnRuleRepository INSTANCE = new VpnRuleRepository();

    private final List<VpnRule> rules = new ArrayList<>();

    private VpnRuleRepository() {
        rules.add(new VpnRule("改写 verifycontent",
                "host=app.dakabg.com & path contains /mobile/check",
                "query.verifycontent = AA0DC6D1F80024160F25627413D2BD23", true));
        rules.add(new VpnRule("登录接口打标",
                "host=auth.example.com & method=POST",
                "header.X-Debug-Trace = skeleton", true));
        rules.add(new VpnRule("屏蔽无关上报",
                "host=metrics.example.com",
                "drop request", false));
    }

    public static VpnRuleRepository getInstance() {
        return INSTANCE;
    }

    public List<VpnRule> getRules() {
        return Collections.unmodifiableList(rules);
    }
}
