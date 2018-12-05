package com.sogou.sogocommon.net;

import java.util.HashMap;
import java.util.Map;

/**
 * created by zhouwei 2018/6/15
 */
public class HttpRequestHeader{
    private Map<String, String> mHeaders;

    private HttpRequestHeader(Builder builder) {
        this.mHeaders = builder.mHeaders;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public static final class Builder {
        private Map<String, String> mHeaders;

        public Builder() {
            mHeaders = new HashMap<>();
        }

        public Builder addHeader(String name, String value) {
            checkNotNull(name, value);
            mHeaders.put(name, value);
            return this;
        }

        private void checkNotNull(String name, String value) {
            if (name == null) throw new NullPointerException("name can not be null");
            if (value == null) throw new NullPointerException("value can not be null");
        }

        public HttpRequestHeader build() {
            return new HttpRequestHeader(this);
        }
    }
}
