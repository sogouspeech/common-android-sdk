package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/15
 */
public enum  Error {
    NET_ERROR(-1,"net error");

    public int errorCode;
    public String errorMsg;

    Error(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
