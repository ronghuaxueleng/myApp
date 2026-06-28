package com.ronghuaxueleng.activity;

import android.widget.ListView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.adapter.VpnRuleAdapter;
import com.ronghuaxueleng.base.BaseActivity;
import com.ronghuaxueleng.vpn.VpnRuleRepository;

import butterknife.BindView;

public class VpnRuleListActivity extends BaseActivity {
    @BindView(R.id.list_rules)
    ListView listRules;

    @Override
    public int getContentViewId() {
        return R.layout.activity_vpn_rule_list;
    }

    @Override
    public void setListener() {
    }

    @Override
    protected void processLogic() {
        listRules.setAdapter(new VpnRuleAdapter(this, VpnRuleRepository.getInstance().getRules()));
    }

    @Override
    public void onNetCallBack(int command, Object object) {
    }
}
