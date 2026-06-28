package com.ronghuaxueleng.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.VpnService;
import android.os.Build;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.adapter.VpnSessionAdapter;
import com.ronghuaxueleng.base.BaseActivity;
import com.ronghuaxueleng.vpn.VpnCaptureService;
import com.ronghuaxueleng.vpn.VpnSession;
import com.ronghuaxueleng.vpn.VpnSessionRepository;

import java.util.List;

import butterknife.BindView;

public class VpnCaptureActivity extends BaseActivity {
    private static final int REQUEST_VPN_PERMISSION = 2001;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_state)
    TextView textState;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_empty)
    TextView textEmpty;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.list_sessions)
    ListView listSessions;

    private VpnSessionAdapter sessionAdapter;
    private boolean running;
    private final BroadcastReceiver stateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (VpnCaptureService.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                running = intent.getBooleanExtra(VpnCaptureService.EXTRA_RUNNING, false);
                renderState();
            }
        }
    };

    @Override
    public int getContentViewId() {
        return R.layout.activity_vpn_capture;
    }

    @Override
    public void setListener() {
        findViewById(R.id.button_start).setOnClickListener(this);
        findViewById(R.id.button_stop).setOnClickListener(this);
        findViewById(R.id.button_legacy).setOnClickListener(this);
        findViewById(R.id.button_rules).setOnClickListener(this);
        listSessions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VpnSession session = (VpnSession) parent.getItemAtPosition(position);
                Intent intent = new Intent(VpnCaptureActivity.this, VpnSessionDetailActivity.class);
                intent.putExtra(VpnSessionDetailActivity.EXTRA_SESSION_ID, session.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void processLogic() {
        sessionAdapter = new VpnSessionAdapter(this, getSessionData());
        listSessions.setAdapter(sessionAdapter);
        registerReceiver(stateReceiver, new IntentFilter(VpnCaptureService.ACTION_STATE_CHANGED));
        running = VpnSessionRepository.getInstance().isRunning();
        renderState();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_start) {
            prepareVpnPermission();
        } else if (id == R.id.button_stop) {
            stopVpnSkeleton();
        } else if (id == R.id.button_legacy) {
            transfer(MainActivity.class);
        } else if (id == R.id.button_rules) {
            transfer(VpnRuleListActivity.class);
        }
    }

    @Override
    public void onNetCallBack(int command, Object object) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_VPN_PERMISSION && resultCode == RESULT_OK) {
            startVpnSkeleton();
        } else if (requestCode == REQUEST_VPN_PERMISSION) {
            textState.setText(R.string.vpn_state_idle);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(stateReceiver);
        super.onDestroy();
    }

    private void prepareVpnPermission() {
        Intent intent = VpnService.prepare(this);
        if (intent != null) {
            textState.setText(R.string.vpn_state_waiting);
            startActivityForResult(intent, REQUEST_VPN_PERMISSION);
        } else {
            startVpnSkeleton();
        }
    }

    private void startVpnSkeleton() {
        VpnSessionRepository.getInstance().resetToDemoData();
        Intent intent = new Intent(this, VpnCaptureService.class);
        intent.setAction(VpnCaptureService.ACTION_START);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
        running = true;
        refreshSessions();
        renderState();
    }

    private void stopVpnSkeleton() {
        Intent intent = new Intent(this, VpnCaptureService.class);
        intent.setAction(VpnCaptureService.ACTION_STOP);
        startService(intent);
        running = false;
        renderState();
    }

    private void renderState() {
        textState.setText(running ? R.string.vpn_state_running : R.string.vpn_state_idle);
        textEmpty.setVisibility(listSessions.getAdapter() == null || listSessions.getAdapter().getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private List<VpnSession> getSessionData() {
        return VpnSessionRepository.getInstance().getSessions();
    }

    private void refreshSessions() {
        sessionAdapter = new VpnSessionAdapter(this, getSessionData());
        listSessions.setAdapter(sessionAdapter);
    }
}
