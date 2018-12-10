// Copyright 2018 Sogou Inc. All rights reserved. 
// Use of this source code is governed by the Apache 2.0 
// license that can be found in the LICENSE file. 
package com.sogou.sogocommon.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by xuq on 2017/10/10.
 */

public class CommonUtils {

    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        return imei;
    }

    public static boolean isNetworkAvaliable(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager cManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cManager == null) {
            return false;
        }
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            return true;
        }

        return false;
    }

    private static int getNetWorkClass(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return SogoConstants.NetWorkConstants.NETWORK_CLASS_2_G;

            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return SogoConstants.NetWorkConstants.NETWORK_CLASS_3_G;

            case TelephonyManager.NETWORK_TYPE_LTE:
                return SogoConstants.NetWorkConstants.NETWORK_CLASS_4_G;

            default:
                return SogoConstants.NetWorkConstants.NETWORK_CLASS_UNKNOWN;
        }
    }

    public static int getNetWorkStatus(Context context) {
        int netWorkType = SogoConstants.NetWorkConstants.NETWORK_CLASS_UNKNOWN;

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();

            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = SogoConstants.NetWorkConstants.NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetWorkClass(context);
            }
        }

        return netWorkType;
    }

    public static boolean ping(String host, int pingCount, StringBuffer stringBuffer) {
        String line = null;
        Process process = null;
        BufferedReader successReader = null;
//        String command = "ping -c " + pingCount + " -w 5 " + host;
        String command = "ping -c " + pingCount + " " + host;
        boolean isSuccess = false;
        try {
            process = Runtime.getRuntime().exec(command);
            if (process == null) {
                append(stringBuffer, "ping fail:process is null.");
                return false;
            }
            successReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = successReader.readLine()) != null) {
                append(stringBuffer, line);
            }
            int status = process.waitFor();
            if (status == 0) {
                append(stringBuffer, "exec cmd success:" + command);
                isSuccess = true;
            } else {
                append(stringBuffer, "exec cmd fail.");
                isSuccess = false;
            }
            append(stringBuffer, "exec finished.");
        } catch (IOException e) {
        } catch (InterruptedException e) {
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (successReader != null) {
                try {
                    successReader.close();
                } catch (IOException e) {
                }
            }
        }
        return isSuccess;
    }

    private static void append(StringBuffer stringBuffer, String text) {
        if (stringBuffer != null) {
            stringBuffer.append(text + "\n");
        }
    }

    public static String getUTCTime() throws ParseException {
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));

//Local time zone
        SimpleDateFormat dateFormatGMT = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGMT.setTimeZone(TimeZone.getTimeZone("GMT"));

        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
//Time in GMT
//        LogUtil.log("xq","dateFormatUTC.format(new Date());  "+dateFormatUTC.format(new Date()));
//        LogUtil.log("xq","dateFormatGMT.format(new Date());  "+dateFormatGMT.format(new Date()));
//        LogUtil.log("xq","dateFormatLocal.format(new Date());  "+dateFormatLocal.format(new Date()));
        return dateFormatUTC.format(new Date());
    }

    public static boolean ifFetchToken(Context context,boolean force){
        if(force){
            return true;
        }
        if(TextUtils.isEmpty(CommonSharedPreference.getInstance(context).getString(CommonSharedPreference.TOKEN,""))){
            return true;
        }
        if(CommonSharedPreference.getInstance(context).getLong(CommonSharedPreference.TIMEOUT_STAMP,0L) <100){
            return true;
        }
        long timestamp = System.currentTimeMillis();
//        LogUtil.log("xq","timestamp is "+timestamp);
//        LogUtil.log("xq","TIMEOUT_STAMP is "+CommonSharedPreference.getInstance(context).getLong(CommonSharedPreference.TIMEOUT_STAMP,0L)*1000);
//        LogUtil.log("xq","isFetchToken "+(timestamp < CommonSharedPreference.getInstance(context).getLong(CommonSharedPreference.TIMEOUT_STAMP,0L)*1000?false:true));
//        return true;
        return timestamp < CommonSharedPreference.getInstance(context).getLong(CommonSharedPreference.TIMEOUT_STAMP,0L)*1000?false:true;
    }
    public static String getApplicationMetaData(Context context, String key){
        String value = "";
        if(context == null){
            return value;
        }
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            if (appInfo == null) {
                return value;
            }
            Bundle applicationMetaData = appInfo.metaData;
            if (applicationMetaData == null) {
                return value;
            }
//            mAppId = applicationMetaData.getString("com.sogou.speech.trsdk-appID");
//            mAccessKey = applicationMetaData.getString("com.sogou.speech.trsdk-appkey");
            value = applicationMetaData.getString(key);
//            mPackageName = context.getPackageName();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String getAppProcessName(Context context) {
        //当前应用pid
        int pid = android.os.Process.myPid();
        //任务管理类
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //遍历所有应用
        List<ActivityManager.RunningAppProcessInfo> infos = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == pid)//得到当前应用
                return info.processName;//返回包名
        }
        return "";
    }
}