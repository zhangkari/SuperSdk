package com.karic.sdk.sdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupListener();
    }

    private void setupListener() {
        View.OnClickListener listener = new View.OnClickListener() {
            int platform, type;

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_admob_banner:
                        platform = AppConstants.AD_ADMOB;
                        type = AppConstants.AD_BANNER;
                        break;

                    case R.id.tv_admob_splash:
                        platform = AppConstants.AD_ADMOB;
                        type = AppConstants.AD_SPLASH;
                        break;

                    case R.id.tv_admob_native:
                        platform = AppConstants.AD_ADMOB;
                        type = AppConstants.AD_NATIVE;
                        break;

                    case R.id.tv_fb_banner:
                        platform = AppConstants.AD_FB;
                        type = AppConstants.AD_BANNER;
                        break;

                    case R.id.tv_fb_splash:
                        platform = AppConstants.AD_FB;
                        type = AppConstants.AD_SPLASH;
                        break;

                    case R.id.tv_fb_native:
                        platform = AppConstants.AD_FB;
                        type = AppConstants.AD_NATIVE;
                        break;
                }

                gotoShowAd(platform, type);
            }
        };

        findViewById(R.id.tv_admob_native).setOnClickListener(listener);
        findViewById(R.id.tv_admob_splash).setOnClickListener(listener);
        findViewById(R.id.tv_admob_banner).setOnClickListener(listener);
        findViewById(R.id.tv_fb_banner).setOnClickListener(listener);
        findViewById(R.id.tv_fb_native).setOnClickListener(listener);
        findViewById(R.id.tv_fb_splash).setOnClickListener(listener);
    }

    private void gotoShowAd(@AppConstants.AdPlatform int platform, @AppConstants.AdType int type) {
        Intent intent = new Intent(this, AdDisplayActivity.class);
        intent.putExtra("platform", platform)
                .putExtra("type", type);
        startActivity(intent);
    }
}
