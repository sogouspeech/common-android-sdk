package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/15
 */
public interface ResponseListener<T> {
    void publishProgress(int total,int current);

    void onSuccess(T result);

    void onError(int errorCode,String errorMsg);
}
