package com.ronghuaxueleng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.vpn.VpnSession;

import java.util.List;

public class VpnSessionAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final List<VpnSession> sessions;

    public VpnSessionAdapter(Context context, List<VpnSession> sessions) {
        this.inflater = LayoutInflater.from(context);
        this.sessions = sessions;
    }

    @Override
    public int getCount() {
        return sessions.size();
    }

    @Override
    public Object getItem(int position) {
        return sessions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_vpn_session, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        VpnSession session = sessions.get(position);
        holder.textMethod.setText(session.getMethod());
        holder.textHost.setText(session.getHost());
        holder.textPath.setText(session.getPath());
        holder.textStatus.setText(session.getStatus());
        holder.textSummary.setText(session.getSummary());
        return convertView;
    }

    private static class ViewHolder {
        private final TextView textMethod;
        private final TextView textHost;
        private final TextView textPath;
        private final TextView textStatus;
        private final TextView textSummary;

        private ViewHolder(View root) {
            textMethod = root.findViewById(R.id.text_method);
            textHost = root.findViewById(R.id.text_host);
            textPath = root.findViewById(R.id.text_path);
            textStatus = root.findViewById(R.id.text_status);
            textSummary = root.findViewById(R.id.text_summary);
        }
    }
}
