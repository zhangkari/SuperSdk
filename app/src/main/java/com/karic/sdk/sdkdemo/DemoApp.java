package com.karic.sdk.sdkdemo;

import android.app.Application;

import com.karic.sdk.AdSdk;

public class DemoApp extends Application {
    private AdSdk adSdk;

    @Override
    public void onCreate() {
        super.onCreate();
        adSdk = AdSdk.Settings.testFbOnly();
        adSdk.init(this);
    }

    public AdSdk getAdSdk() {
        return adSdk;
    }
}
