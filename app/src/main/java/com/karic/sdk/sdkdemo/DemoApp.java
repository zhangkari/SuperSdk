package com.karic.sdk.sdkdemo;

import android.app.Application;

import com.karic.sdk.SuperAdSdk;

public class DemoApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SuperAdSdk.init(this, "ad.config.json");
    }
}
