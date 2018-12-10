// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.utils;

import android.text.TextUtils;
import android.util.Log;

public class LogUtil {
    private static final String DEFAULT_TAG = "SogoSpeech";
    private static boolean isDebug = true;

    public static boolean isIsDebug() {
        return isDebug;
    }

    public static void setIsDebug(boolean isDebug) {
        LogUtil.isDebug = isDebug;
    }

    public static void v(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.v(tag, msg);
        }
    }

    public static void v(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.v(DEFAULT_TAG, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.d(tag, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.d(DEFAULT_TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.i(DEFAULT_TAG, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.w(tag, msg);
        }
    }

    public static void w(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.w(DEFAULT_TAG, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (isDebug && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(tag, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug && !TextUtils.isEmpty(msg)) {
            Log.e(DEFAULT_TAG, msg);
        }
    }
}