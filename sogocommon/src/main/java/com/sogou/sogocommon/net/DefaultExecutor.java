package com.sogou.sogocommon.net;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * created by zhouwei 2018/6/15
 */

public class DefaultExecutor implements IExecutor {
    private static ThreadPoolExecutor executor = null;

    @Override
    public void execute(Runnable runnable) {
        if (null != runnable) {
            executor.execute(runnable);
            executor.shutdown();
        }
    }

    @Override
    public void shutdown() {
        if (null != executor) {
            executor.shutdown();
        }
    }

    @Override
    public void shutdownNow() {
        if (null != executor) {
            executor.shutdownNow();
        }
    }

    private DefaultExecutor() {
        executor = new ThreadPoolExecutor(40, 40,
                60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        executor.allowCoreThreadTimeOut(true);
    }

    private static class ExecutorHolder {
        public static DefaultExecutor executor = new DefaultExecutor();
    }

    public static DefaultExecutor getInstance() {
        return ExecutorHolder.executor;
    }
}
