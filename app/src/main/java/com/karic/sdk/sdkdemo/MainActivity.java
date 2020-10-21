package com.karic.sdk.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.karic.sdk.AdSdk;
import com.karic.sdk.AidBox;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewGroup admobContainer = findViewById(R.id.admob_container);
        findViewById(R.id.btn_admob).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "btn_admob onClick");
                getAdSdkAdmob().showSplashAd(v.getContext(), AidBox.testAdmobSplashOnly());
                getAdSdkAdmob().showBannerAd(admobContainer, AidBox.testAdmobBannerOnly());
            }
        });

        final ViewGroup fbContainer = findViewById(R.id.fb_container);
        findViewById(R.id.btn_fb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "btn_fb onClick");
                getAdSdkFb().showSplashAd(v.getContext(), AidBox.testFbSplashOnly());
                getAdSdkFb().showBannerAd(fbContainer, AidBox.testFbBannerOnly());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getAdSdkFb().resumeBannerAd();
        getAdSdkAdmob().resumeBannerAd();
    }

    @Override
    public void onPause() {
        super.onPause();

        getAdSdkFb().pauseBannerAd();
        getAdSdkAdmob().pauseBannerAd();
    }

    @Override
    public void onDestroy() {
        getAdSdkAdmob().destroyBannerAd();
        getAdSdkAdmob().destroySplashAd();

        getAdSdkFb().destroySplashAd();
        getAdSdkFb().destroyBannerAd();

        super.onDestroy();
    }

    private AdSdk getAdSdkFb() {
        return ((DemoApp) getApplication()).getAdSdkTestFbOnly();
    }

    private AdSdk getAdSdkAdmob() {
        return ((DemoApp) getApplication()).getAdSdkTestAdmobOnly();
    }
}
