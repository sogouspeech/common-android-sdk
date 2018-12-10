// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.net;

import android.text.TextUtils;
import android.util.Log;

import com.sogou.sogocommon.utils.CloseUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * created by zhouwei 2018/6/15
 */
public abstract class Worker implements Runnable {
    protected String TAG = Worker.class.getSimpleName();

    private static final String BOUNDARY = "**************##################**************";
    private static final String NEWLINE = "\r\n";
    private static final String PREFIX = "--";

    protected HttpURLConnection conn;

    protected InputStream inputStream;

    protected Request request;

    protected ResponseListener responseListener;

    protected int retryCount = 0;

    protected Worker(Request request) {
        if (null == request) {
            throw new RuntimeException("request is null");
        }
        this.request = request;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    protected HttpURLConnection openConnection(Request request) throws Exception {
        if (null == request) return null;
        if (RequestMethod.GET.getValue().compareToIgnoreCase(request.method.getValue()) == 0) {
            String paramsStr = request.generateParamsString();
            if (!TextUtils.isEmpty(paramsStr)) {
                request.url = request.url + "?" + paramsStr;
            }
        }

        HttpURLConnection conn = createConnection(new URL(request.url));

        conn.setConnectTimeout(request.connectTimeoutMS);
        conn.setReadTimeout(request.readTimeoutMS);
        conn.setRequestMethod(request.method.getValue());

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setRequestProperty("Accept-Charset", request.charset);
        conn.setRequestProperty("Accept-Encoding", "identity");
        conn.setRequestProperty("Connection", "Keep-Alive");

        if (request.httpRequestHeader != null) {
            setHeader(conn, request.httpRequestHeader);
        }

        if (RequestMethod.POST.getValue().compareToIgnoreCase(request.method.getValue()) == 0) {
            String paramStr = request.generateParamsString();
            if (!TextUtils.isEmpty(paramStr)) {
                conn.setDoOutput(true);
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(paramStr.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }

        return conn;
    }

    protected HttpURLConnection createConnection(URL url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(HttpURLConnection.getFollowRedirects());
        return connection;
    }

    protected void setHeader(HttpURLConnection conn, HttpRequestHeader httpRequestHeader) {
        if (null != httpRequestHeader) {
            Map<String, String> header = httpRequestHeader.getHeaders();
            if (header != null && header.size() > 0) {
                for (Map.Entry<String, String> en : header.entrySet()) {
                    String key = en.getKey();
                    String value = en.getValue();
                    conn.setRequestProperty(key, value);
                    Log.i(TAG, "header: " + value);
                }
            }
        }
    }

    protected String inStream2String(InputStream inputStream) throws Exception {
        String res = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[32 * 1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
                bos.flush();
            }
            res = bos.toString();
        } finally {
            CloseUtil.close(new OutputStream[]{bos});
        }

        return res;
    }

    protected void printHeaders(HttpURLConnection conn) {
        Map<String, List<String>> map = conn.getHeaderFields();
        Log.i(TAG, "map size: " + map.size());

        Set<String> set = map.keySet();
        for (String key : set) {
            List<String> list = map.get(key);
            for (String value : list) {
                Log.i(TAG, "key=" + key + "  value=" + value);
            }
        }
    }

    @Override
    public void run() {

    }
}