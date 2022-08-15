package com.ronghuaxueleng.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.base.BaseFragment;
import com.ronghuaxueleng.bean.LoginMessage;
import com.ronghuaxueleng.bean.basebean.ParamsBuilder;
import com.ronghuaxueleng.common.PARAMS;
import com.ronghuaxueleng.model.ModelSuperImpl;
import com.ronghuaxueleng.model.NetWorkListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import butterknife.BindView;



public class GETFragment extends BaseFragment implements NetWorkListener {
    final int GANK_COMMAND = 99;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_signin)
    TextView txt_get;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_content)
    TextView txt_content;
    private StringBuffer sbf;

    public static Fragment newFragment() {
        return new GETFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_get;
    }

    @Override
    protected void setListener() {
        txt_get.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_signin) {
            sbf = new StringBuffer();
            ModelSuperImpl.netWork().gankGet(ParamsBuilder.build().params(PARAMS.gank("android"))
                    .command(GANK_COMMAND), this);
        }
    }

    @Override
    public void onNetCallBack(int command, Object object) {
        if (command == GANK_COMMAND) {
            String result = (String) object;
            sbf.append(result + "\n");
            txt_content.setText(sbf.toString());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onbackEvent(LoginMessage message) {
        if (message.getType().equals("GET")) {
            sbf.append(message.getMessage() + "\n");
            txt_content.setText(sbf.toString());
        }

    }


}
