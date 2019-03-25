package com.karic.sdk.sdkdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karic.sdk.AdSdk;
import com.karic.sdk.AidBox;

public class MainActivity extends Activity {

    private AdSdk adSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adSdk = getAdSdk();

        final ViewGroup adContainer = findViewById(R.id.ad_container);
        TextView tv = findViewById(R.id.tv_splash);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "onClick");
                adSdk.showSplashAd(v.getContext(), AidBox.testAdmobSplashOnly());
                adSdk.showBannerAd(adContainer, AidBox.testAdmobBannerOnly());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adSdk.resumeBannerAd();
    }

    @Override
    public void onPause() {
        super.onPause();
        adSdk.pauseBannerAd();
    }

    @Override
    public void onDestroy() {
        adSdk.destroySplashAd();
        adSdk.destroyBannerAd();
        super.onDestroy();
    }

    public AdSdk getAdSdk() {
        return ((DemoApp) getApplication()).getAdSdk();
    }
}
