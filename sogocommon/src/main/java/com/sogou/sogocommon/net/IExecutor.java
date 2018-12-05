package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/14
 */
public interface IExecutor {
    void execute(Runnable runnable);
    void shutdown();
    void shutdownNow();
}
