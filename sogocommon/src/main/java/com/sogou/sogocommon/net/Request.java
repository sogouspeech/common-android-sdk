package com.sogou.sogocommon.net;

import android.text.TextUtils;
import android.util.Log;


import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * created by zhouwei 2018/6/14
 */

public abstract class Request {
    private static AtomicInteger sequenceGenerator = new AtomicInteger(0);
    protected int mSerialNumber = 0;

    protected String charset = "UTF-8";

    protected RequestMethod method = RequestMethod.GET;

    protected String url;

    protected HttpRequestHeader httpRequestHeader = null;

    protected int readTimeoutMS = 10 * 1000;

    protected int connectTimeoutMS = 10 * 1000;

    protected boolean mShouldCache = false;

    protected Map<String, String> params = new HashMap<>();

    protected Worker task;

    private Request() {
        setWorker();
        mSerialNumber = generateSerialNumber();
    }

    public Request(String url, RequestMethod requestMethod) {
        this();
        this.method = requestMethod;
        this.url = url;
    }

    public Request(String url) {
        this();
        this.url = url;
    }

    public Request setParams(String key, String value) {
        if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value)) {
            params.put(key, value);
        }

        return this;
    }

    protected String generateParamsString() {
        String res = null;
        if (params.size() > 0) {
            try {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, String> en : params.entrySet()) {
                    String k = en.getKey();
                    String v = en.getValue();
                    sb.append(URLEncoder.encode(k, charset));
                    sb.append('=');
                    sb.append(URLEncoder.encode(v, charset));
                    sb.append('&');
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                res = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.i("Request", "ParamsString: " + res);
        return res;
    }

    public abstract void setWorker();

    public int getSerialNumber() {
        return mSerialNumber;
    }

    protected int generateSerialNumber() {
        return sequenceGenerator.incrementAndGet();
    }

    public RequestMethod getRequestMethodd() {
        return method;
    }

    public void setRequestMethod(RequestMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public HttpRequestHeader getHttpRequestHeader() {
        return httpRequestHeader;
    }

    public void setHttpRequestHeader(HttpRequestHeader httpRequestHeader) {
        this.httpRequestHeader = httpRequestHeader;
    }

    public Worker getTask() {
        return task;
    }
}

