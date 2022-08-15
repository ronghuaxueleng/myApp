package com.ronghuaxueleng.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.base.BaseFragment;
import com.ronghuaxueleng.model.DakaModelImpl;
import com.ronghuaxueleng.model.NetWorkListener;
import com.ronghuaxueleng.utils.DateUtils;
import com.ronghuaxueleng.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;



public class DakaFragment extends BaseFragment {
    final int GANK_COMMAND = 99;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_signin)
    TextView txt_signin;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_signout)
    TextView txt_signout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_content)
    TextView txt_content;

    public static Fragment newFragment() {
        return new DakaFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_daka;
    }

    @Override
    protected void setListener() {
        txt_signin.setOnClickListener(this);
        txt_signout.setOnClickListener(this);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
    }


    int viewId;
    String tenantId;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_signin:
            case R.id.txt_signout:
                viewId = v.getId();
                DakaModelImpl.netWork().checkver(GANK_COMMAND, new CheckVerNetWorkListener());
        }
    }

    class CheckVerNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                String result = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject version = jsonObject.getJSONObject("version");
                    String versionno = version.getString("versionno");
                    DakaModelImpl.netWork().mobilefwd(GANK_COMMAND, new MobilefwdNetWorkListener(), versionno);
                    LogUtils.i("请求结果", versionno);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MobilefwdNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                String result = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray tenantlist = jsonObject.getJSONArray("tenantlist");
                    JSONObject tenant = tenantlist.getJSONObject(0);
                    tenantId = tenant.getString("tenantid");
                    DakaModelImpl.netWork().getchecktimes(GANK_COMMAND, new GetCheckTimesNetWorkListener(), tenantId);
                    LogUtils.i("请求结果", tenantId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class GetCheckTimesNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                String result = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject checktimes = jsonObject.getJSONObject("checktimes");
                    if (viewId == R.id.txt_signin) {
                        boolean needcheckin = checktimes.getBoolean("needcheckin");
                        if (needcheckin) {
                            DakaModelImpl.netWork().signin(GANK_COMMAND, new SignInNetWorkListener(), tenantId);
                        } else {
                            DakaModelImpl.netWork().checklist(GANK_COMMAND, new CheckListWorkListener(), tenantId);
                        }
                    } else if (viewId == R.id.txt_signout) {
                        String todayDate = DateUtils.getNowDate();
                        String formatToday = String.format("%s 18:00:00", todayDate);
                        long timeStamp = DateUtils.getTimeStamp(formatToday);
                        if (System.currentTimeMillis() - timeStamp >= 0) {
                            boolean needcheckout = checktimes.getBoolean("needcheckout");
                            if (needcheckout) {
                                DakaModelImpl.netWork().worklist(GANK_COMMAND, new WorklistNetWorkListener(), tenantId);
                            } else {
                                DakaModelImpl.netWork().checklist(GANK_COMMAND, new CheckListWorkListener(), tenantId);
                            }
                        } else {
                            txt_content.setText("未到签退时间");
                        }
                    }
                    LogUtils.i("请求结果", String.valueOf(jsonObject));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class WorklistNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                String result = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray projectworks = jsonObject.getJSONArray("projectworks");
                    JSONObject work = projectworks.getJSONObject(0);
                    String id = work.getString("id");
                    String projectid = work.getString("projectid");
                    DakaModelImpl.netWork().signout(GANK_COMMAND, new SignOutNetWorkListener(), tenantId, id, projectid);
                    txt_content.setText(work.toString());
                    LogUtils.i("请求结果", work.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class SignOutNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                String result = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    txt_content.setText(jsonObject.toString());
                    LogUtils.i("请求结果", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class SignInNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                String result = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    txt_content.setText(jsonObject.toString());
                    LogUtils.i("请求结果", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class CheckListWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                String result = (String) object;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray checklist = jsonObject.getJSONArray("checklist");
                    StringBuilder sbf = new StringBuilder();
                    for (int i = 0; i < checklist.length(); i++) {
                        JSONObject json = checklist.getJSONObject(i);
                        int checktype = json.getInt("checktype");
                        long checktime = json.getLong("checktime");
                        Date date = new Date(checktime);
                        String dateFormat = format.format(date);
                        sbf.append(checktype == 0 ? "签到时间：" : "签退时间：").append(dateFormat).append("\n");
                    }
                    txt_content.setText(sbf.toString());
                    LogUtils.i("请求结果", jsonObject.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
