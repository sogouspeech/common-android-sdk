package com.sogou.sogocommon.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by xuq on 2017/12/15.
 */

public class CommonSharedPreference {

    private static final String TAG = "CommonPreferences";
    private static final String PREFRENCE_NAME = "CommonSharePrefrence";

    public static final String TOKEN = "TOKEN";
    public static final String CERTIFICATE = "CERTIFICATE";
    public static final String TIMEOUT_STAMP = "TIMEOUT_STAMP";
    public static final String SERVER_DNS = "SERVER_DNS";
    public static final String LAST_BSSID = "LAST_BSSID";

    public static final String CONTINIOUS_WAKEUP = "CONTINIOUS_WAKEUP";
    public static final String WAKEUP_IS_NEEDED = "WAKEUP_IS_NEEDED";

    private static CommonSharedPreference m_stInstance;
    private Context mContext;
    private SharedPreferences mPrefs;

    public CommonSharedPreference(Context context) {
        mContext = context.getApplicationContext();
    }


    public static CommonSharedPreference getInstance(Context context) {
        if (m_stInstance == null)
            m_stInstance = new CommonSharedPreference(context);

        return m_stInstance;
    }

    public SharedPreferences getmPrefs() {
        if (mPrefs == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mPrefs = mContext.getSharedPreferences(PREFRENCE_NAME, Context.MODE_MULTI_PROCESS);
            } else {
                mPrefs = mContext
                        .getSharedPreferences(PREFRENCE_NAME, Context.MODE_WORLD_WRITEABLE);
            }
        }
        return mPrefs;
    }

    public String getString(String key, String defaultVaule) {
        if (mPrefs == null) {
            mPrefs = getmPrefs();
        }
        return mPrefs.getString(key, defaultVaule);
    }

    public void setString(String key, String vaule) {
        if (mPrefs == null) {
            mPrefs = getmPrefs();
        }
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(key, vaule);
        editor.commit();
    }

    public Long getLong(String key, Long defaultVaule) {
        if (mPrefs == null) {
            mPrefs = getmPrefs();
        }
        return mPrefs.getLong(key, defaultVaule);
    }

    public void setLong(String key, Long vaule) {
        if (mPrefs == null) {
            mPrefs = getmPrefs();
        }
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putLong(key, vaule);
        editor.commit();
    }

    public int getInt(String key, int defaultVaule) {
        if (mPrefs == null) {
            mPrefs = getmPrefs();
        }
        return mPrefs.getInt(key, defaultVaule);
    }

    public boolean setInt(String key, int vaule) {
        if (mPrefs == null) {
            mPrefs = getmPrefs();
        }
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putInt(key, vaule);
        boolean ret = editor.commit();
        return ret;
    }

    public boolean getBoolean(String key, boolean defaultVaule) {
        if (mPrefs == null) {
            mPrefs = getmPrefs();
        }
        return mPrefs.getBoolean(key, defaultVaule);
    }

    public boolean setBoolean(String key, boolean vaule) {
        if (mPrefs == null) {
            mPrefs = getmPrefs();
        }
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(key, vaule);
        boolean ret = editor.commit();
        return ret;
    }

    public void clear() {
        if (mPrefs == null) {
            mPrefs = getmPrefs();
        }
        mPrefs.edit().clear().commit();
    }

}
