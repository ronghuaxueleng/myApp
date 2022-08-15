package com.ronghuaxueleng.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ronghuaxueleng.R;
import com.ronghuaxueleng.base.BaseFragment;
import com.ronghuaxueleng.bean.LoginMessage;
import com.ronghuaxueleng.bean.basebean.ParamsBuilder;
import com.ronghuaxueleng.model.ModelSuperImpl;
import com.ronghuaxueleng.model.NetWorkListener;
import com.ronghuaxueleng.model.PermissionListener;
import com.ronghuaxueleng.okhttps.EasyOk;
import com.ronghuaxueleng.okhttps.okcallback.OnDownloadListener;
import com.ronghuaxueleng.utils.MathUtils;
import com.ronghuaxueleng.utils.OpenFileUtils;
import com.ronghuaxueleng.utils.PreferenceUtil;
import com.ronghuaxueleng.utils.ToastUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import butterknife.BindView;



public class DownFileFragment extends BaseFragment implements OnDownloadListener, PermissionListener, NetWorkListener {
    final int NORMAL_COMMAND = 99;
    final int RESUME_COMMAND = 100;
    private final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ok_down/demo/";
    private final String fileName = "easyOk.apk";
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_down)
    TextView txt_down;//正常下载.
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_resumdown)
    TextView txt_resumdown;//断点下载
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_cancledown)
    TextView txt_cancledown;//暂停下载
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_content)
    TextView txt_content;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_findfile)
    TextView txt_findfile;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_delete)
    TextView txt_delete;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_total)
    TextView txt_total;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_current)
    TextView txt_current;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txt_file)
    TextView txt_file;

    public static Fragment newFragment() {
        return new DownFileFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_downfile;
    }

    @Override
    protected void setListener() {
        txt_down.setOnClickListener(this);
        txt_resumdown.setOnClickListener(this);
        txt_cancledown.setOnClickListener(this);
        txt_findfile.setOnClickListener(this);
        txt_delete.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void processLogic(Bundle savedInstanceState) {
        txt_cancledown.setEnabled(false);
        File file = new File(path, fileName);
        if (file.exists()) {
            long currentfile = file.length();
            long total = (long) PreferenceUtil.get("totalApk", currentfile);
            int present = (int) (currentfile * 100 / total);
            progressBar.setProgress(present);
            txt_current.setText(present + "%");
            txt_total.setText(MathUtils.round((double) total / 1024 / 1024, 2) + "M");
            txt_file.setText("文件路径 ==> " + file.getAbsolutePath() + "\n" + "文件目前大小 ==> " + MathUtils.round((double) file.length() / 1024 / 1024, 2) + "M)");
        } else {
            txt_file.setText("文件不存在");
        }
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_down:
                ModelSuperImpl.permission().requestPermission(NORMAL_COMMAND, this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                setCancle(true);
                break;
            case R.id.txt_resumdown:
                ModelSuperImpl.permission().requestPermission(RESUME_COMMAND, this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
                setCancle(true);
                break;
            case R.id.txt_cancledown:
                EasyOk.getInstance().cancleOkhttpTag("downApk");
                setCancle(false);
                break;

            case R.id.txt_findfile:
                openAndroidFile(new File(path, fileName));
                break;
            case R.id.txt_delete:
                File file = new File(path, fileName);
                if (file.exists()) {
                    if (file.delete()) {
                        ToastUtils.showToast("删除成功");
                        progressBar.setProgress(0);
                        txt_current.setText("0%");
                        txt_file.setText("文件不存在");
                    }
                }
                break;
        }
    }

    @Override
    public void onNetCallBack(int command, Object object) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onbackEvent(LoginMessage message) {

    }


    @Override
    public void onDownloadSuccess(File file) {
        ToastUtils.showToast("下载成功");
        setCancle(false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDownLoadTotal(long total) {
        txt_total.setText(MathUtils.round((double) total / 1024 / 1024, 2) + "M");
        PreferenceUtil.put("totalApk", total);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDownloading(int progress) {
        progressBar.setProgress(progress);
        txt_current.setText(progress + "%");
        txt_content.setText("当前下载进度 ==> " + progress);
        File file = new File(path, fileName);
        txt_file.setText("文件路径 ==> " + file.getAbsolutePath() + "\n" + "目前文件大小 ==> " + MathUtils.round((double) file.length() / 1024 / 1024, 2) + "M");
    }

    @Override
    public void onDownloadFailed(Exception e) {
        ToastUtils.showToast("下载失败 == >" + e.toString());
    }


    //这是打开文件的方式，并不是正确的安装apk。。
    public void openAndroidFile(File file) {
        if (!file.exists()) {
            ToastUtils.showToast("文件不存在");
            return;
        }
        Intent intent = new Intent();
        // 这是比较流氓的方法，绕过7.0的文件权限检查
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);//动作，查看ACTION_VIEW
        intent.setDataAndType(Uri.fromFile(file), OpenFileUtils.getMIMEType(file));//设置类型OpenFileUtils.getMIMEType(file)
        startActivity(intent);
    }


    @Override
    public void permissionSuccess(int command) {
        switch (command) {
            case NORMAL_COMMAND:
                ModelSuperImpl.netWork().downApk(ParamsBuilder.build().path(path)
                        .fileName(fileName).tag("downApk"), this);
                break;
            case RESUME_COMMAND:
                ModelSuperImpl.netWork().downApk(ParamsBuilder.build().path(path)
                        .fileName(fileName).tag("downApk").resume(true), this);
                break;
        }
    }

    @Override
    public void permissionFail(int command) {
        ToastUtils.showToast("文件下载，需要此权限");
    }

    public void setCancle(boolean cancle){
        txt_down.setEnabled(!cancle);
        txt_resumdown.setEnabled(!cancle);
        txt_findfile.setEnabled(!cancle);
        txt_delete.setEnabled(!cancle);
        txt_cancledown.setEnabled(cancle);
    }
}
