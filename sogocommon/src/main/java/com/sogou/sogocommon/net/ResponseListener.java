// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.net;

/**
 * created by zhouwei 2018/6/15
 */
public interface ResponseListener<T> {
    void publishProgress(int total,int current);

    void onSuccess(T result);

    void onError(int errorCode,String errorMsg);
}