// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/15
 */
public class DefaultRetryPolicy implements IRetryPolicy {
    @Override
    public void retry(Request request) {

    }

    private DefaultRetryPolicy() {
    }

    private static class DefaultRetryPolicyHolder {
        public static DefaultRetryPolicy instance = new DefaultRetryPolicy();
    }

    public static IRetryPolicy getInstance() {
        return DefaultRetryPolicyHolder.instance;
    }
}