// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/15
 */
public final class CommonRequest extends Request {
    public CommonRequest(String url) {
        super(url);
    }

    public CommonRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public void setWorker() {
        task = new CommonWorker(this);
    }
}