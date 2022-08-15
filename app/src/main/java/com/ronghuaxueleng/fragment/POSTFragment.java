package com.ronghuaxueleng.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.base.BaseFragment;
import com.ronghuaxueleng.bean.basebean.ParamsBuilder;
import com.ronghuaxueleng.common.PARAMS;
import com.ronghuaxueleng.model.ModelSuperImpl;
import com.ronghuaxueleng.model.NetWorkListener;

import butterknife.BindView;



public class POSTFragment extends BaseFragment implements NetWorkListener {
    final int GANK_COMMAND = 99;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_signin)
    TextView txt_get;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_content)
    TextView txt_content;
    private StringBuffer sbf;

    public static Fragment newFragment() {
        return new POSTFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_get;
    }

    @Override
    protected void setListener() {
        txt_get.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void processLogic(Bundle savedInstanceState) {
        txt_get.setText("POST请求");
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_signin) {
            sbf = new StringBuffer();
            ModelSuperImpl.netWork().gankPost(ParamsBuilder.build().params(PARAMS.gankPost("https://github.com/lihangleo2/ShadowLayout",
                            "阴影布局，不管你是什么控件，放进阴影布局即刻享受你想要的阴影", "110", "Android", "true"))
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

}
