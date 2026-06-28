package com.ronghuaxueleng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.vpn.VpnRule;

import java.util.List;

public class VpnRuleAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final List<VpnRule> rules;

    public VpnRuleAdapter(Context context, List<VpnRule> rules) {
        this.inflater = LayoutInflater.from(context);
        this.rules = rules;
    }

    @Override
    public int getCount() {
        return rules.size();
    }

    @Override
    public Object getItem(int position) {
        return rules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_vpn_rule, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VpnRule rule = rules.get(position);
        holder.textName.setText(rule.getName());
        holder.textMatch.setText(rule.getMatch());
        holder.textAction.setText(rule.getAction());
        holder.textEnabled.setText(rule.isEnabled() ? "ENABLED" : "DISABLED");
        return convertView;
    }

    private static class ViewHolder {
        private final TextView textName;
        private final TextView textMatch;
        private final TextView textAction;
        private final TextView textEnabled;

        private ViewHolder(View root) {
            textName = root.findViewById(R.id.text_rule_name);
            textMatch = root.findViewById(R.id.text_rule_match);
            textAction = root.findViewById(R.id.text_rule_action);
            textEnabled = root.findViewById(R.id.text_rule_enabled);
        }
    }
}
