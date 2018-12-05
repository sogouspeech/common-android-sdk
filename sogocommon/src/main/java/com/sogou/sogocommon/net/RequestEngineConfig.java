package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/14
 */
public class RequestEngineConfig {
    private int readTimeoutMS = 10 * 1000;

    private int connectTimeoutMS = 10 * 1000;

    private IExecutor executor = DefaultExecutor.getInstance();

    private IRetryPolicy retryPolicy = DefaultRetryPolicy.getInstance();

    private ICache cache = DefaultCache.getInstance();

    public static class Builder {
        private int readTimeoutMS = 10 * 1000;

        private int connectTimeoutMS = 10 * 1000;

        private IRetryPolicy retryPolicy;

        private IExecutor executor;

        private ICache cache;

        public Builder readTimeoutMS(int readTimeoutMS) {
            this.readTimeoutMS = readTimeoutMS;
            return this;
        }

        public Builder connectTimeoutMS(int connectTimeoutMS) {
            this.connectTimeoutMS = connectTimeoutMS;
            return this;
        }

        public Builder retryPolicy(IRetryPolicy retryPolicy) {
            this.retryPolicy = retryPolicy;
            return this;
        }

        public Builder executor(IExecutor executor) {
            this.executor = executor;
            return this;
        }

        public Builder cache(ICache cache) {
            this.cache = cache;
            return this;
        }
    }


    public int getReadTimeoutMS() {
        return readTimeoutMS;
    }

    public int getConnectTimeoutMS() {
        return connectTimeoutMS;
    }

    public IRetryPolicy getRetryPolicy() {
        return retryPolicy;
    }

    public IExecutor getExecutor() {
        return executor;
    }

    public ICache getCache() {
        return cache;
    }
}
