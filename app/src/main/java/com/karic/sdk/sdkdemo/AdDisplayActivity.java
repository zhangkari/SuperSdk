package com.karic.sdk.sdkdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karic.sdk.SuperAdSdk;

public class AdDisplayActivity extends AppCompatActivity {
    private static final String TAG = "AdDisplayActivity";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ad_display);
        initViews();
        initListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        SuperAdSdk.getInstance().resumeBannerAd();
    }

    @Override
    public void onPause() {
        super.onPause();
        SuperAdSdk.getInstance().pauseBannerAd();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SuperAdSdk.getInstance().destroyBannerAd();
    }

    private void initViews() {
        int type = getIntent().getIntExtra("type", 0);
        if (type == 0) {
            Toast.makeText(this, "type is invalid !", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        findViewById(R.id.btn_show_splash).setVisibility(View.GONE);
        findViewById(R.id.banner_container).setVisibility(View.GONE);
        findViewById(R.id.native_container).setVisibility(View.GONE);

        switch (type) {
            case AppConstants.AD_SPLASH:
                findViewById(R.id.btn_show_splash).setVisibility(View.VISIBLE);
                SuperAdSdk.getInstance().loadSplashAd(false);
                break;

            case AppConstants.AD_NATIVE:
                findViewById(R.id.native_container).setVisibility(View.VISIBLE);
                break;

            case AppConstants.AD_BANNER:
                findViewById(R.id.banner_container).setVisibility(View.VISIBLE);
                SuperAdSdk.getInstance().showBannerAd(findViewById(R.id.banner_container));
                break;
        }
    }

    private void initListener() {
        findViewById(R.id.btn_show_splash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuperAdSdk.getInstance().showSplashAd();
            }
        });
    }
}
