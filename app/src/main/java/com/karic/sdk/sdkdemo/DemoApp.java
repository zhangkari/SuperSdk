package com.karic.sdk.sdkdemo;

import android.app.Application;

import com.karic.sdk.AdSdk;

public class DemoApp extends Application {
    private AdSdk adSdkFb;
    private AdSdk adSdkAdmob;

    @Override
    public void onCreate() {
        super.onCreate();
        adSdkFb = AdSdk.Settings.testFbOnly();
        adSdkFb.init(this);

        adSdkAdmob = AdSdk.Settings.testAdmobOnly();
        adSdkAdmob.init(this);
    }

    public AdSdk getAdSdkTestFbOnly() {
        return adSdkFb;
    }

    public AdSdk getAdSdkTestAdmobOnly() {
        return adSdkAdmob;
    }
}
