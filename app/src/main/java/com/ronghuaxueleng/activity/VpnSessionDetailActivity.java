package com.ronghuaxueleng.activity;

import android.widget.TextView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.base.BaseActivity;
import com.ronghuaxueleng.vpn.VpnSession;
import com.ronghuaxueleng.vpn.VpnSessionRepository;

import butterknife.BindView;

public class VpnSessionDetailActivity extends BaseActivity {
    public static final String EXTRA_SESSION_ID = "extra_session_id";

    @BindView(R.id.text_overview)
    TextView textOverview;
    @BindView(R.id.text_request_headers)
    TextView textRequestHeaders;
    @BindView(R.id.text_request_body)
    TextView textRequestBody;
    @BindView(R.id.text_response_headers)
    TextView textResponseHeaders;
    @BindView(R.id.text_response_body)
    TextView textResponseBody;

    @Override
    public int getContentViewId() {
        return R.layout.activity_vpn_session_detail;
    }

    @Override
    public void setListener() {
    }

    @Override
    protected void processLogic() {
        String sessionId = getIntent().getStringExtra(EXTRA_SESSION_ID);
        VpnSession session = VpnSessionRepository.getInstance().findById(sessionId);
        if (session == null) {
            textOverview.setText("Session not found");
            return;
        }
        String overview = session.getMethod() + " " + session.getHost() + session.getPath()
                + "\n" + session.getProtocol() + " · " + session.getStatus()
                + "\n" + session.getSummary();
        textOverview.setText(overview);
        textRequestHeaders.setText(session.getRequestHeaders());
        textRequestBody.setText(session.getRequestBody());
        textResponseHeaders.setText(session.getResponseHeaders());
        textResponseBody.setText(session.getResponseBody());
    }

    @Override
    public void onNetCallBack(int command, Object object) {
    }
}
