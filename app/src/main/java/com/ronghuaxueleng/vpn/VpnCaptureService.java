package com.ronghuaxueleng.vpn;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.VpnService;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.activity.VpnCaptureActivity;
import com.ronghuaxueleng.vpn.core.VpnCaptureEngine;
import com.ronghuaxueleng.vpn.core.VpnEventDispatcher;
import com.ronghuaxueleng.vpn.rule.DefaultRuleMatcher;
import com.ronghuaxueleng.vpn.rule.PassThroughRuleModifier;
import com.ronghuaxueleng.vpn.rule.RuleEngine;

public class VpnCaptureService extends VpnService {
    public static final String ACTION_START = "com.ronghuaxueleng.vpn.START";
    public static final String ACTION_STOP = "com.ronghuaxueleng.vpn.STOP";
    public static final String ACTION_STATE_CHANGED = "com.ronghuaxueleng.vpn.STATE_CHANGED";
    public static final String EXTRA_RUNNING = "extra_running";

    private static final String CHANNEL_ID = "vpn_capture_channel";
    private static final int NOTIFICATION_ID = 1001;
    private VpnCaptureEngine captureEngine;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent == null ? null : intent.getAction();
        if (ACTION_STOP.equals(action)) {
            stopSelf();
            return START_NOT_STICKY;
        }
        VpnEventDispatcher dispatcher = VpnEventDispatcher.getInstance();
        dispatcher.register(VpnSessionRepository.getInstance());
        if (captureEngine == null) {
            captureEngine = new VpnCaptureEngine(dispatcher,
                    new RuleEngine(new DefaultRuleMatcher(), new PassThroughRuleModifier()));
        }
        VpnSessionRepository.getInstance().setRunning(true);
        startForeground(NOTIFICATION_ID, buildNotification());
        captureEngine.start();
        notifyStateChanged(true);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (captureEngine != null) {
            captureEngine.stop();
            captureEngine = null;
        }
        VpnEventDispatcher.getInstance().unregister(VpnSessionRepository.getInstance());
        VpnSessionRepository.getInstance().setRunning(false);
        notifyStateChanged(false);
        stopForeground(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    private Notification buildNotification() {
        createNotificationChannel();
        Intent intent = new Intent(this, VpnCaptureActivity.class);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, flags);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.vpn_notification_title))
                .setContentText(getString(R.string.vpn_notification_text))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager == null) {
            return;
        }
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                getString(R.string.vpn_notification_title), NotificationManager.IMPORTANCE_LOW);
        manager.createNotificationChannel(channel);
    }

    private void notifyStateChanged(boolean isRunning) {
        Intent broadcast = new Intent(ACTION_STATE_CHANGED);
        broadcast.putExtra(EXTRA_RUNNING, isRunning);
        sendBroadcast(broadcast);
    }
}
