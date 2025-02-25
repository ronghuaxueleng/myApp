package com.ronghuaxueleng.model;

import com.ronghuaxueleng.bean.basebean.ParamsBuilder;
import com.ronghuaxueleng.utils.Base64Utils;

import java.util.HashMap;

import okhttp3.MediaType;


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
    private final String myphone = Base64Utils.decodeToString("MTU5MDEyNTQ2ODA=");
    private final String location = Base64Utils.decodeToString("5YyX5Lqs5biC5rW35reA5Yy65b+X5by65Y2X5Zut5LmF5YW26L2v5Lu2KOaWh+aFp+WbreWKnuWFrOWMuik=");

    /**
     * 检查版本信息
     */
    public void checkver(int command, NetWorkListener netWorkListener) {
        String checkver = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9jaGVja3Zlcj9tb2JpbGVvcz0wJnZlcnNpb249MTc1JnRpbWVzdGFtcD0lcw==";
        String url = String.format(Base64Utils.decodeToString(checkver), System.currentTimeMillis());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "inst=inst2");
        headers.put("Cam-Charset", "utf-8");
        headers.put("Host", host);
        headers.put("Content-Type", "text/plain");
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        paramsBuilder.url(url).heads(headers).paramType(2).mediaType(MediaType.parse("text/plain; charset=utf-8")).json(url);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void mobilefwd(int command, NetWorkListener netWorkListener, String versionno) {
        String mobilefwd = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGVmd2Q/dmVyaWZ5dHlwZT0wJnZlcmlmeWNvbnRlbnQ9JXMmbW9iaWxlb3M9MCZ2ZXJzaW9uPSVzJnRpbWVzdGFtcD0lcw==";
        String url = String.format(Base64Utils.decodeToString(mobilefwd), myphone, versionno, System.currentTimeMillis());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "inst=inst2");
        headers.put("Cam-Charset", "utf-8");
        headers.put("Host", host);
        headers.put("Content-Type", "text/plain");
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        paramsBuilder.url(url).heads(headers).paramType(2).mediaType(MediaType.parse("text/plain; charset=utf-8")).json(url);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void getchecktimes(int command, NetWorkListener netWorkListener, String tenantId) {
        String getchecktimes = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGUvZ2V0Y2hlY2t0aW1lcz92ZXJpZnl0eXBlPTAmdmVyaWZ5Y29udGVudD0lcyZ0ZW5hbnRpZD0lcyZ0aW1lc3RhbXA9JXM=";
        String url = String.format(Base64Utils.decodeToString(getchecktimes), myphone, tenantId, System.currentTimeMillis());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "inst=inst2");
        headers.put("Cam-Charset", "utf-8");
        headers.put("Host", host);
        headers.put("Content-Type", "text/plain");
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        paramsBuilder.url(url).heads(headers).paramType(2).mediaType(MediaType.parse("text/plain; charset=utf-8")).json("{\"querycount \":true,\"staffid\":\"613E771AE00000016956919D61C25D9B\"}");
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void signin(int command, NetWorkListener netWorkListener, String tenantId) {
        String signin = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGUvY2hlY2s/dmVyaWZ5dHlwZT0wJnZlcmlmeWNvbnRlbnQ9JXMmdGVuYW50aWQ9JXMmdGltZXN0YW1wPSVz";
        String url = String.format(Base64Utils.decodeToString(signin), myphone, tenantId, System.currentTimeMillis());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "inst=inst2");
        headers.put("Cam-Charset", "utf-8");
        headers.put("Host", host);
        headers.put("Content-Type", "text/plain");
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        String bodyParams = "{\"checktype\":0,\"lng\":116.3730870922896,\"lat\":39.957986127185045,\"location\":\"" + location + "\",\"projectid\":\"8E3BC40D00000081143DFCE92513ED0E\",\"turnname\":\"\",\"method\":7,\"accuracy\":21.64131736755371,\"memo\":\"\",\"picnum\":0,\"ssid\":\"" + ssid + "\",\"mac\":\"84:d9:31:06:5b:40\",\"version\":120}";
        paramsBuilder.url(url).heads(headers).paramType(2).mediaType(MediaType.parse("text/plain; charset=utf-8")).json(bodyParams);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void signout(int command, NetWorkListener netWorkListener, String tenantId, String id, String projectId) {
        String signout = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGUvY2hlY2s/dmVyaWZ5dHlwZT0wJnZlcmlmeWNvbnRlbnQ9JXMmdGVuYW50aWQ9JXMmdGltZXN0YW1wPSVz";
        String url = String.format(Base64Utils.decodeToString(signout), myphone, tenantId, System.currentTimeMillis());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "inst=inst2");
        headers.put("Cam-Charset", "utf-8");
        headers.put("Host", host);
        headers.put("Content-Type", "text/plain");
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        String bodyParams = String.format("{\"checktype\":1,\"lng\":116.37288190005114,\"lat\":39.9579479565396,\"location\":\"" + location + "\",\"works\":[{\"id\":\"%s\",\"hours\":\"8\",\"remark\":\"\",\"projectid\":\"%s\"}],\"turnname\":\"\",\"method\":7,\"accuracy\":29,\"memo\":\"\",\"picnum\":0,\"ssid\":\"" + ssid + "\",\"mac\":\"84:d9:31:06:98:c0\",\"version\":120}", id, projectId);
        paramsBuilder.url(url).heads(headers).paramType(2).mediaType(MediaType.parse("text/plain; charset=utf-8")).json(bodyParams);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void checklist(int command, NetWorkListener netWorkListener, String tenantId) {
        String checklist = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGUvY2hlY2tsaXN0P3ZlcmlmeXR5cGU9MCZ2ZXJpZnljb250ZW50PSVzJnRlbmFudGlkPSVzJnRpbWVzdGFtcD0lcw==";
        String url = String.format(Base64Utils.decodeToString(checklist), myphone, tenantId, System.currentTimeMillis());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "inst=inst2");
        headers.put("Cam-Charset", "utf-8");
        headers.put("Host", host);
        headers.put("Content-Type", "text/plain");
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        String bodyParams = "{\"offset\":0,\"limit\":-1}";
        paramsBuilder.url(url).heads(headers).paramType(2).mediaType(MediaType.parse("text/plain; charset=utf-8")).json(bodyParams);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

    public void worklist(int command, NetWorkListener netWorkListener, String tenantId) {
        String worklist = "aHR0cHM6Ly9hcHAuZGFrYWJnLmNvbS9tb2JpbGUvcHJvamVjdC93b3JrbGlzdD92ZXJpZnl0eXBlPTAmdmVyaWZ5Y29udGVudD0lcyZ0ZW5hbnRpZD0lcyZ0aW1lc3RhbXA9JXM=";
        String url = String.format(Base64Utils.decodeToString(worklist), myphone, tenantId, System.currentTimeMillis());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "inst=inst2");
        headers.put("Cam-Charset", "utf-8");
        headers.put("Host", host);
        headers.put("Content-Type", "text/plain");
        ParamsBuilder paramsBuilder = ParamsBuilder.build().command(command);
        String bodyParams = "{\"limit\":1,\"offset\":0,\"filter\":0}";
        paramsBuilder.url(url).heads(headers).paramType(2).mediaType(MediaType.parse("text/plain; charset=utf-8")).json(bodyParams);
        sendOkHttpPost(paramsBuilder, netWorkListener);
    }

}
