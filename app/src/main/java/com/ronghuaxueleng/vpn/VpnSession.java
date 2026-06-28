package com.ronghuaxueleng.vpn;

public class VpnSession {
    private final String id;
    private final String method;
    private final String protocol;
    private final String host;
    private final String path;
    private final String status;
    private final String summary;
    private final String requestHeaders;
    private final String requestBody;
    private final String responseHeaders;
    private final String responseBody;

    public VpnSession(String id, String method, String protocol, String host, String path, String status,
                      String summary, String requestHeaders, String requestBody, String responseHeaders, String responseBody) {
        this.id = id;
        this.method = method;
        this.protocol = protocol;
        this.host = host;
        this.path = path;
        this.status = status;
        this.summary = summary;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public String getId() {
        return id;
    }

    public String getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public String getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }

    public String getRequestHeaders() {
        return requestHeaders;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public String getResponseHeaders() {
        return responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
