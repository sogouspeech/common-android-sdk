package com.sogou.sogocommon.net;

import java.net.HttpURLConnection;

/**
 * created by zhouwei 2018/6/15
 */

final class CommonWorker extends Worker {
    public CommonWorker(CommonRequest request) {
        super(request);
    }

    @Override
    public void run() {
        while (true){
            try {
                executeRequest();
                break;
            }catch (Exception e){
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
        try {
            conn = openConnection(request);
            if (null == conn) throw new RuntimeException("conn == null");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = conn.getInputStream();
                String res = inStream2String(inputStream);

                if (null != res && null != responseListener) {
                    responseListener.onSuccess(res);
                }

                if (null != res && request.mShouldCache) {
                    RequestEngine.getInstance().cacheData(request.url, res);
                }
            } else {
                throw new RuntimeException("responseCode: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != conn) {
                conn.disconnect();
                conn = null;
            }
        }
    }
}
