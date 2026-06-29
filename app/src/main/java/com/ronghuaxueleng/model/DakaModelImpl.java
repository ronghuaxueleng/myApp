package com.ronghuaxueleng.model;

import com.ronghuaxueleng.bean.basebean.ParamsBuilder;
import com.ronghuaxueleng.utils.Base64Utils;
import com.ronghuaxueleng.utils.LogUtils;

import java.util.HashMap;

import okhttp3.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DakaModelImpl extends ModelBase {
    private static final DakaModelImpl ourInstance = new DakaModelImpl();

    public static DakaModelImpl netWork() {
        return ourInstance;
    }

    public static ModelPermissionImpl permission() {
        return new ModelPermissionImpl();
    }

    private DakaModelImpl() {
    }

    private final String host = Base64Utils.decodeToString("YXBwLmRha2FiZy5jb20=");
    private final String ssid = Base64Utils.decodeToString("Sml1UWktT2ZmaWNl");
    private final String verifycontent = "8F8057E632341985408C6829111F4673";
    private final String location = Base64Utils.decodeToString("5YyX5Lqs5biC5rW35reA5Yy65b+X5by65Y2X5Zut5LmF5YW26L2v5Lu2KOaWh+aFp+WbreWKnuWFrOWMuik=");
    private final String mac = "2a:b2:b9:3c:a7:97";
    private final String phonetype = "BMH-AN20";
    private final String signoutProjectId = "8E3BFAF2A0000041AD09C5CD47A605A1";
    private final double signinLng = 116.37291;
    private final double signinLat = 39.957899;
    private final double signoutLng = 116.372943;
    private final double signoutLat = 39.957884;
    private final int method = 1;
    private final int accuracy = 40;
    private final int mockprobability = -1;
    private final int version = 120;
    private final String myphone = Base64Utils.decodeToString("MTU5MDEyNTQ2ODA=");

    /**
     * 检查版本信息
     */
    public void checkver(int command, NetWorkListener netWorkListener) {
        String checkver = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9jaGVja3Zlcj9tb2JpbGVvcz0wJnZlcnNpb249MTc1JnRpbWVzdGFtcD0lcw==";
        String url = String.format(Base64Utils.decodeToString(checkver), System.currentTimeMillis());
        LogUtils.i("打卡请求", "checkver url=" + url);
        HashMap<String, String> headers = buildHeaders();
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        paramsBuilder.url(url)
                .heads(headers)
                .paramType(2)
                .mediaType(MediaType.parse("text/plain; charset=utf-8"))
                .overrideError(true)
                .json(url);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void mobilefwd(int command, NetWorkListener netWorkListener, String versionno) {
        String mobilefwd = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGVmd2Q/dmVyaWZ5dHlwZT0wJnZlcmlmeWNvbnRlbnQ9JXMmbW9iaWxlb3M9MCZ2ZXJzaW9uPSVzJnRpbWVzdGFtcD0lcw==";
        String url = String.format(Base64Utils.decodeToString(mobilefwd), myphone, versionno, System.currentTimeMillis());
        LogUtils.i("打卡请求", "mobilefwd url=" + url);
        HashMap<String, String> headers = buildHeaders();
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        paramsBuilder.url(url)
                .heads(headers)
                .paramType(2)
                .mediaType(MediaType.parse("text/plain; charset=utf-8"))
                .overrideError(true)
                .json(url);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void getchecktimes(int command, NetWorkListener netWorkListener, String tenantId) {
        String getchecktimes = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGUvZ2V0Y2hlY2t0aW1lcz92ZXJpZnl0eXBlPTAmdmVyaWZ5Y29udGVudD0lcyZ0ZW5hbnRpZD0lcyZ0aW1lc3RhbXA9JXM=";
        String url = String.format(Base64Utils.decodeToString(getchecktimes), myphone, tenantId, System.currentTimeMillis());
        LogUtils.i("打卡请求", "getchecktimes tenantId=" + tenantId);
        LogUtils.i("打卡请求", "getchecktimes url=" + url);
        HashMap<String, String> headers = buildHeaders();
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        String body = "{\"querycount \":true,\"staffid\":\"613E771AE00000016956919D61C25D9B\"}";
        LogUtils.i("打卡请求", "getchecktimes body=" + body);
        paramsBuilder.url(url)
                .heads(headers)
                .paramType(2)
                .mediaType(MediaType.parse("text/plain; charset=utf-8"))
                .overrideError(true)
                .json(body);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void signin(int command, NetWorkListener netWorkListener, String tenantId) {
        String signin = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGUvY2hlY2s/dmVyaWZ5dHlwZT0xJnZlcmlmeWNvbnRlbnQ9JXMmdGVuYW50aWQ9JXMmdGltZXN0YW1wPSVz";
        String url = String.format(Base64Utils.decodeToString(signin), verifycontent, tenantId, System.currentTimeMillis());
        LogUtils.i("打卡请求", "signin tenantId=" + tenantId);
        LogUtils.i("打卡请求", "signin url=" + url);
        HashMap<String, String> headers = buildHeaders();
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        String bodyParams = buildSigninBodyParams();
        LogUtils.i("打卡请求", "signin body=" + bodyParams);
        paramsBuilder.url(url)
                .heads(headers)
                .paramType(2)
                .mediaType(MediaType.parse("text/plain; charset=utf-8"))
                .overrideError(true)
                .json(bodyParams);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void signout(int command, NetWorkListener netWorkListener, String tenantId) {
        String signout = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGUvY2hlY2s/dmVyaWZ5dHlwZT0xJnZlcmlmeWNvbnRlbnQ9JXMmdGVuYW50aWQ9JXMmdGltZXN0YW1wPSVz";
        String url = String.format(Base64Utils.decodeToString(signout), verifycontent, tenantId, System.currentTimeMillis());
        LogUtils.i("打卡请求", "signout tenantId=" + tenantId);
        LogUtils.i("打卡请求", "signout url=" + url);
        HashMap<String, String> headers = buildHeaders();
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        String bodyParams = buildSignoutBodyParams();
        LogUtils.i("打卡请求", "signout body=" + bodyParams);
        paramsBuilder.url(url)
                .heads(headers)
                .paramType(2)
                .mediaType(MediaType.parse("text/plain; charset=utf-8"))
                .overrideError(true)
                .json(bodyParams);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void checklist(int command, NetWorkListener netWorkListener, String tenantId) {
        String checklist = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGUvY2hlY2tsaXN0P3ZlcmlmeXR5cGU9MCZ2ZXJpZnljb250ZW50PSVzJnRlbmFudGlkPSVzJnRpbWVzdGFtcD0lcw==";
        String url = String.format(Base64Utils.decodeToString(checklist), myphone, tenantId, System.currentTimeMillis());
        LogUtils.i("打卡请求", "checklist tenantId=" + tenantId);
        LogUtils.i("打卡请求", "checklist url=" + url);
        HashMap<String, String> headers = buildHeaders();
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        String bodyParams = "{\"offset\":0,\"limit\":-1}";
        LogUtils.i("打卡请求", "checklist body=" + bodyParams);
        paramsBuilder.url(url)
                .heads(headers)
                .paramType(2)
                .mediaType(MediaType.parse("text/plain; charset=utf-8"))
                .overrideError(true)
                .json(bodyParams);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    private HashMap<String, String> buildHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "inst=inst2");
        headers.put("Cam-Charset", "utf-8");
        headers.put("Host", host);
        headers.put("Content-Type", "text/plain");
        return headers;
    }

    private String buildSigninBodyParams() {
        try {
            JSONObject body = buildCommonCheckBody(signinLng, signinLat, 0);
            body.put("facestate", 2);
            return body.toString();
        } catch (JSONException e) {
            throw new IllegalStateException("build signin body failed", e);
        }
    }

    private String buildSignoutBodyParams() {
        try {
            JSONObject work = new JSONObject();
            work.put("hours", "8");
            work.put("remark", "");
            work.put("onsite", false);
            work.put("projectid", signoutProjectId);

            JSONArray works = new JSONArray();
            works.put(work);

            JSONObject body = buildCommonCheckBody(signoutLng, signoutLat, 1);
            body.put("works", works);
            body.put("memo", "");
            body.put("picnum", 0);
            return body.toString();
        } catch (JSONException e) {
            throw new IllegalStateException("build signout body failed", e);
        }
    }

    private JSONObject buildCommonCheckBody(double lng, double lat, int checktype) throws JSONException {
        JSONObject body = new JSONObject();
        body.put("checktype", checktype);
        body.put("lng", lng);
        body.put("lat", lat);
        body.put("mockprobability", mockprobability);
        body.put("location", location);
        body.put("turnname", "");
        body.put("method", method);
        body.put("accuracy", accuracy);
        body.put("ssid", ssid);
        body.put("mac", mac);
        body.put("phonetype", phonetype);
        body.put("version", version);
        return body;
    }
}
