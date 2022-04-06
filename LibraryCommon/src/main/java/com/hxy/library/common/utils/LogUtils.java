package com.hxy.library.common.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Huang on 2016/3/22.
 */
public class LogUtils {


    public static boolean isDebug = true;
    private static final String TAG = "huang_app_log";

    public LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void isDebug(boolean isDebug) {
        LogUtils.isDebug = isDebug;
    }

    public static void v(String str) {
        if (isDebug && !TextUtils.isEmpty(str)) {
            Log.v(TAG, str);
        }
    }

    public static void d(String str) {
        if (isDebug && !TextUtils.isEmpty(str)) {
            Log.d(TAG, str);
        }
    }

    public static void i(String str) {
        if (isDebug && !TextUtils.isEmpty(str)) {
            Log.i(TAG, str);
        }
    }

    public static void e(String str) {
        if (isDebug && !TextUtils.isEmpty(str)) {
            Log.e(TAG, str);
        }
    }

    public static void v(String TAG, String str) {
        if (isDebug && !TextUtils.isEmpty(str)) {
            Log.v(TAG, str);
        }
    }

    public static void d(String TAG, String str) {
        if (isDebug && !TextUtils.isEmpty(str)) {
            Log.d(TAG, str);
        }
    }

    public static void i(String TAG, String str) {
        if (isDebug && !TextUtils.isEmpty(str)) {
            Log.i(TAG, str);
        }
    }
    public static void w(String TAG, String str) {
        if (isDebug && !TextUtils.isEmpty(str)) {
            Log.w(TAG, str);
        }
    }
    public static void e(String TAG, String str) {
        if (isDebug && !TextUtils.isEmpty(str)) {
            Log.e(TAG, str);
        }
    }
}
