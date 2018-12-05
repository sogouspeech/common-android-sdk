package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/15
 */
public final class CommonRequest extends Request {
    public CommonRequest(String url) {
        super(url);
    }

    public CommonRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public void setWorker() {
        task = new CommonWorker(this);
    }
}
