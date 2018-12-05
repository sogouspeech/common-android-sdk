package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/14
 */
public interface IRetryPolicy {
    void retry(Request request);
}
