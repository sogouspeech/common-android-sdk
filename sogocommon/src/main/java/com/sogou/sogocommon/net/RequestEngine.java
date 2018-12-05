package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/14
 */
public class RequestEngine {
    private IExecutor executor = DefaultExecutor.getInstance();

    private IRetryPolicy retryPolicy = DefaultRetryPolicy.getInstance();

    private ICache cache = DefaultCache.getInstance();

    public synchronized void init(RequestEngineConfig requestEngineConfig) {
        executor = requestEngineConfig.getExecutor();
        retryPolicy = requestEngineConfig.getRetryPolicy();
        cache = requestEngineConfig.getCache();
    }

    public void postRequest(Request request, ResponseListener responseListener) {
        if (request != null) {
            if (request.mShouldCache) {
                Object res = cache.fetch(request.url);
                if (null != res) {
                    if (responseListener != null) {
                        responseListener.onSuccess(res);
                    }
                    return;
                }
            }

            request.task.setResponseListener(responseListener);
            executor.execute(request.task);
        }
    }


    public void cacheData(String k, String v) {
        cache.store(k, v);
    }

    public void retry(Request request) {
        retryPolicy.retry(request);
    }

    public static RequestEngine getInstance() {
        return RequestEngineHolder.instance;
    }

    private static class RequestEngineHolder {
        public static RequestEngine instance = new RequestEngine();
    }

    private RequestEngine() {

    }
}
