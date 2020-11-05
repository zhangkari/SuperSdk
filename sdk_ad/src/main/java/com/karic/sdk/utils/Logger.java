package com.karic.sdk.utils;

import android.util.Log;

import com.karic.sdk.sdk_ad.BuildConfig;

public final class Logger {
    private static final String TAG = "SuperAdSdk";

    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }
}
