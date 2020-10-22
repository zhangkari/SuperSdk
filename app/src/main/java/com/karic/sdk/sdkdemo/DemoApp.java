package com.karic.sdk.sdkdemo;

import android.app.Application;

import com.karic.sdk.AdSdk;

public class DemoApp extends Application {
    private AdSdk adSdkFb;
    private AdSdk adSdkAdmob;

    @Override
    public void onCreate() {
        super.onCreate();
        adSdkFb = AdSdk.Settings.testFb();
        adSdkFb.init(this);

        adSdkAdmob = AdSdk.Settings.testAdmob();
        adSdkAdmob.init(this);
    }

    public AdSdk getAdSdkTestFb() {
        return adSdkFb;
    }

    public AdSdk getAdSdkTestAdmob() {
        return adSdkAdmob;
    }
}
