// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.net;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * created by zhouwei 2018/6/15
 */

final class UploadFileWorker extends Worker {
    private static final String BOUNDARY = "---------------------------106850529911103040791395246970";
    private static final String NEWLINE = "\r\n";
    private static final String PREFIX = "--";

    private UploadFileRequest uploadFileRequest;

    protected UploadFileWorker(Request request) {
        super(request);
        uploadFileRequest = (UploadFileRequest) request;
    }

    @Override
    public void run() {
        while (true) {
            try {
                executeRequest();
                break;
            } catch (Exception e) {
                e.printStackTrace();
                if (++retryCount >= 3) {
                    if (null != responseListener) {
                        responseListener.onError(Error.NET_ERROR.errorCode, Error.NET_ERROR.errorMsg);
                    }

                    RequestEngine.getInstance().retry(request);
                    break;
                }
            }
        }
    }

    private void executeRequest() {
        DataOutputStream dos = null;
        try {
            URL urll = new URL(request.url);
            conn = (HttpURLConnection) urll.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.87 Safari/537.36");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            conn.connect();

            dos = new DataOutputStream(conn.getOutputStream());

            if (uploadFileRequest.formParams != null && !uploadFileRequest.formParams.isEmpty()) {
                for (Map.Entry<String, String> entry : uploadFileRequest.formParams.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    dos.writeBytes(PREFIX + BOUNDARY + NEWLINE);
                    dos.writeBytes("Content-Disposition: form-data; " + "name=\"" + key + "\"" + NEWLINE);
                    dos.writeBytes(NEWLINE);
                    dos.writeBytes(URLEncoder.encode(value.toString(), request.charset));
                    dos.writeBytes(NEWLINE);
                }
                dos.flush();
            }

            if (uploadFileRequest.attachment != null && uploadFileRequest.attachment.length > 0) {
                dos.writeBytes(PREFIX + BOUNDARY + NEWLINE);
                dos.writeBytes("Content-Disposition: form-data; " + "name=\""
                        + "uploadFile\"" + "; filename=\"" + uploadFileRequest.attachmentName + "\""
                        + NEWLINE);
                dos.writeBytes("Content-Type: text/plain" + NEWLINE);
                dos.writeBytes(NEWLINE);
                dos.write(uploadFileRequest.attachment);
                dos.writeBytes(NEWLINE);
            }
            dos.writeBytes(PREFIX + BOUNDARY + PREFIX + NEWLINE);
            dos.flush();

            if (conn.getResponseCode() == 200) {
                String res = inStream2String(conn.getInputStream());

                if (null != res && null != responseListener) {
                    responseListener.onSuccess(res);
                }

                if (null != res && request.mShouldCache) {
                    RequestEngine.getInstance().cacheData(request.url, res);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }

                if (null != dos) {
                    dos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}