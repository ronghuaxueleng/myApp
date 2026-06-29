package com.ronghuaxueleng.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.base.BaseFragment;
import com.ronghuaxueleng.bean.basebean.NetFailBean;
import com.ronghuaxueleng.model.DakaModelImpl;
import com.ronghuaxueleng.model.NetWorkListener;
import com.ronghuaxueleng.utils.DateUtils;
import com.ronghuaxueleng.utils.LogUtils;
import com.ronghuaxueleng.utils.ToastUtils;

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
                tenantId = null;
                txt_content.setText(viewId == R.id.txt_signin ? "正在签到..." : "正在签退...");
                LogUtils.i("打卡流程", viewId == R.id.txt_signin ? "点击签到" : "点击签退");
                DakaModelImpl.netWork().checkver(GANK_COMMAND, new CheckVerNetWorkListener());
                break;
            default:
                break;
        }
    }

    class CheckVerNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                if (object instanceof NetFailBean) {
                    String message = ((NetFailBean) object).getMessage();
                    txt_content.setText("检查版本失败：" + message);
                    LogUtils.e("打卡流程", "checkver failed: " + message);
                    return;
                }
                String result = (String) object;
                LogUtils.i("打卡流程", "checkver response: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject version = jsonObject.getJSONObject("version");
                    String versionno = version.getString("versionno");
                    LogUtils.i("打卡流程", "checkver success, versionno=" + versionno);
                    DakaModelImpl.netWork().mobilefwd(GANK_COMMAND, new MobilefwdNetWorkListener(), versionno);
                    LogUtils.i("请求结果", versionno);
                } catch (JSONException e) {
                    txt_content.setText("检查版本解析失败：" + e.getMessage() + "\n" + trimResult(result));
                    LogUtils.e("打卡流程", "checkver parse failed: " + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    class MobilefwdNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                if (object instanceof NetFailBean) {
                    String message = ((NetFailBean) object).getMessage();
                    txt_content.setText("获取租户失败：" + message);
                    LogUtils.e("打卡流程", "mobilefwd failed: " + message);
                    return;
                }
                String result = (String) object;
                LogUtils.i("打卡流程", "mobilefwd response: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray tenantlist = jsonObject.getJSONArray("tenantlist");
                    if (tenantlist.length() == 0) {
                        LogUtils.e("打卡流程", "mobilefwd tenantlist empty");
                        txt_content.setText("获取租户失败：tenantlist 为空\n" + trimResult(result));
                        return;
                    }
                    JSONObject tenant = tenantlist.getJSONObject(0);
                    tenantId = tenant.getString("tenantid");
                    if (tenantId == null || tenantId.trim().isEmpty()) {
                        LogUtils.e("打卡流程", "mobilefwd tenantid empty");
                        txt_content.setText("获取租户失败：tenantid 为空\n" + trimResult(result));
                        return;
                    }
                    LogUtils.i("打卡流程", "mobilefwd success, tenantId=" + tenantId);
                    txt_content.setText("正在获取打卡状态...");
                    DakaModelImpl.netWork().getchecktimes(GANK_COMMAND, new GetCheckTimesNetWorkListener(), tenantId);
                    LogUtils.i("请求结果", tenantId);
                } catch (JSONException e) {
                    txt_content.setText("获取租户解析失败：" + e.getMessage() + "\n" + trimResult(result));
                    LogUtils.e("打卡流程", "mobilefwd parse failed: " + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    class GetCheckTimesNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                if (object instanceof NetFailBean) {
                    String message = ((NetFailBean) object).getMessage();
                    txt_content.setText("获取打卡状态失败：" + message);
                    LogUtils.e("打卡流程", "getchecktimes failed: " + message);
                    return;
                }
                String result = (String) object;
                LogUtils.i("打卡流程", "getchecktimes response: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.has("checktimes")) {
                        LogUtils.e("打卡流程", "getchecktimes missing checktimes field");
                        txt_content.setText("获取打卡状态失败：缺少 checktimes\n" + trimResult(result));
                        return;
                    }
                    JSONObject checktimes = jsonObject.getJSONObject("checktimes");
                    if (viewId == R.id.txt_signin) {
                        boolean needcheckin = checktimes.getBoolean("needcheckin");
                        LogUtils.i("打卡流程", "getchecktimes success, needcheckin=" + needcheckin);
                        if (needcheckin) {
                            txt_content.setText("正在签到，提交打卡中...");
                            DakaModelImpl.netWork().signin(GANK_COMMAND, new SignInNetWorkListener(), tenantId);
                        } else {
                            txt_content.setText("正在签到，读取签到记录...");
                            DakaModelImpl.netWork().checklist(GANK_COMMAND, new CheckListWorkListener(), tenantId);
                        }
                    } else if (viewId == R.id.txt_signout) {
                        String todayDate = DateUtils.getNowDate();
                        String formatToday = String.format("%s 18:00:00", todayDate);
                        long timeStamp = DateUtils.getTimeStamp(formatToday);
                        if (System.currentTimeMillis() - timeStamp >= 0) {
                            boolean needcheckout = checktimes.getBoolean("needcheckout");
                            LogUtils.i("打卡流程", "getchecktimes success, needcheckout=" + needcheckout);
                            if (needcheckout) {
                                txt_content.setText("正在签退，提交打卡中...");
                                DakaModelImpl.netWork().signout(GANK_COMMAND, new SignOutNetWorkListener(), tenantId);
                            } else {
                                txt_content.setText("正在签退，读取签退记录...");
                                DakaModelImpl.netWork().checklist(GANK_COMMAND, new CheckListWorkListener(), tenantId);
                            }
                        } else {
                            txt_content.setText("未到签退时间");
                        }
                    }
                    LogUtils.i("请求结果", String.valueOf(jsonObject));
                } catch (JSONException e) {
                    txt_content.setText("解析打卡状态失败：" + e.getMessage() + "\n" + trimResult(result));
                    LogUtils.e("打卡流程", "getchecktimes parse failed: " + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    class SignOutNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                if (object instanceof NetFailBean) {
                    String message = ((NetFailBean) object).getMessage();
                    txt_content.setText("签退失败：" + message);
                    LogUtils.e("打卡流程", "signout failed: " + message);
                    return;
                }
                String result = (String) object;
                LogUtils.i("打卡流程", "signout response: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    LogUtils.i("打卡流程", "signout success");
                    txt_content.setText(jsonObject.toString());
                    LogUtils.i("请求结果", jsonObject.toString());
                } catch (JSONException e) {
                    txt_content.setText("签退解析失败：" + e.getMessage() + "\n" + trimResult(result));
                    LogUtils.e("打卡流程", "signout parse failed: " + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    class SignInNetWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                if (object instanceof NetFailBean) {
                    String message = ((NetFailBean) object).getMessage();
                    txt_content.setText("签到失败：" + message);
                    LogUtils.e("打卡流程", "signin failed: " + message);
                    return;
                }
                String result = (String) object;
                LogUtils.i("打卡流程", "signin response: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    LogUtils.i("打卡流程", "signin success");
                    txt_content.setText(jsonObject.toString());
                    LogUtils.i("请求结果", jsonObject.toString());
                } catch (JSONException e) {
                    txt_content.setText("签到解析失败：" + e.getMessage() + "\n" + trimResult(result));
                    LogUtils.e("打卡流程", "signin parse failed: " + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    class CheckListWorkListener implements NetWorkListener {
        @Override
        public void onNetCallBack(int command, Object object) {
            if (command == GANK_COMMAND) {
                if (object instanceof NetFailBean) {
                    String message = ((NetFailBean) object).getMessage();
                    txt_content.setText("获取记录失败：" + message);
                    LogUtils.e("打卡流程", "checklist failed: " + message);
                    return;
                }
                String result = (String) object;
                LogUtils.i("打卡流程", "checklist response: " + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray checklist = jsonObject.getJSONArray("checklist");
                    LogUtils.i("打卡流程", "checklist success, size=" + checklist.length());
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
                    txt_content.setText("解析打卡记录失败：" + e.getMessage() + "\n" + trimResult(result));
                    LogUtils.e("打卡流程", "checklist parse failed: " + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }

    private String trimResult(String result) {
        if (result == null) {
            return "";
        }
        return result.length() > 500 ? result.substring(0, 500) + "..." : result;
    }

}
