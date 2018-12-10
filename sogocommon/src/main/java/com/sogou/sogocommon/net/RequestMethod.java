// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
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