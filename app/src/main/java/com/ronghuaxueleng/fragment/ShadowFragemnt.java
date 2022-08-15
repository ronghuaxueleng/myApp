package com.ronghuaxueleng.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.base.BaseFragment;
import com.ronghuaxueleng.bean.LoginMessage;
import com.ronghuaxueleng.model.NetWorkListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ShadowFragemnt extends BaseFragment implements NetWorkListener {

    public static Fragment newFragment() {
        return new ShadowFragemnt();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_shadow;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onNetCallBack(int command, Object object) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onbackEvent(LoginMessage message) {

    }
}
