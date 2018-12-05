package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/14
 */
public enum RequestMethod {
    GET("GET"), POST("POST");

    private String value;

    RequestMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
