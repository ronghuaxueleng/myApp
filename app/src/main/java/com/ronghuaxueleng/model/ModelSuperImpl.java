package com.ronghuaxueleng.model;

import android.util.Pair;

import com.ronghuaxueleng.bean.basebean.ParamsBuilder;
import com.ronghuaxueleng.common.SystemConst;
import com.ronghuaxueleng.okhttps.okcallback.OnDownloadListener;

import java.io.File;
import java.util.ArrayList;


public class ModelSuperImpl extends ModelBase {
    private static final ModelSuperImpl ourInstance = new ModelSuperImpl();

    public static ModelSuperImpl netWork() {
        return ourInstance;
    }

    public static ModelPermissionImpl permission() {
        return new ModelPermissionImpl();
    }

    private ModelSuperImpl() {

    }

    //模拟搜索方法,外面参数过多，如果在多个页面都用到这个网络请求，把重复的写在里面
    public void gankGet(ParamsBuilder paramsBuilder, NetWorkListener netWorkListener) {
        paramsBuilder.url(SystemConst.GANK_GET)
//                .type(new TypeToken<ResponModel<String>>() {
//                }.getType())
        ;
        sendOkHttpGet(paramsBuilder, netWorkListener);
    }


    public void gankPost(ParamsBuilder paramsBuilder, NetWorkListener netWorkListener) {
        paramsBuilder.url(SystemConst.GANK_POST)
//                .type(new TypeToken<ResponModel<User>>() {
//                }.getType())
        ;
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }


    public void downApk(ParamsBuilder paramsBuilder, OnDownloadListener onDownloadListener) {
        paramsBuilder.url(SystemConst.QQ_APK);
        sendOkHttpDownload(paramsBuilder, onDownloadListener);
    }


    //不同file 不同key
    @SafeVarargs
    public final void uploadPic(ParamsBuilder paramsBuilder, NetWorkListener netWorkListener, Pair<String, File>... files) {
        paramsBuilder.url(SystemConst.UPLOAD_PIC)
//                .type(new TypeToken<ResponModel<String>>() {
//                }.getType())
        ;
        sendOkHttpUpload(paramsBuilder, netWorkListener, files);
    }

    //同一key 不同file
    public void uploadPic(ParamsBuilder paramsBuilder, NetWorkListener netWorkListener, String key, ArrayList<File> files) {
        paramsBuilder.url(SystemConst.UPLOAD_PIC)
//                .type(new TypeToken<ResponModel<String>>() {
//                }.getType())
        ;
        sendOkHttpUpload(paramsBuilder, netWorkListener, key, files);
    }


}
