package com.ronghuaxueleng.vpn.core;

public class HttpMessage {
    private final String protocol;
    private final String method;
    private final String host;
    private final String path;
    private final String requestHeaders;
    private final String requestBody;
    private final String responseHeaders;
    private final String responseBody;
    private final String status;
    private final String summary;

    public HttpMessage(String protocol, String method, String host, String path,
                       String requestHeaders, String requestBody,
                       String responseHeaders, String responseBody,
                       String status, String summary) {
        this.protocol = protocol;
        this.method = method;
        this.host = host;
        this.path = path;
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.status = status;
        this.summary = summary;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getMethod() {
        return method;
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
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

    public String getStatus() {
        return status;
    }

    public String getSummary() {
        return summary;
    }
}
