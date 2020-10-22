package com.karic.sdk.sdkdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karic.sdk.AdSdk;
import com.karic.sdk.AidBox;
import com.karic.sdk.NativeAd;

public class AdDisplayActivity extends AppCompatActivity {
    private static final String TAG = "AdDisplayActivity";

    private AdSdk adSdk;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ad_display);
        showAd();
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
        super.onDestroy();
        adSdk.destroyBannerAd();
        adSdk.destroySplashAd();
    }

    private void showAd() {
        int platform = getIntent().getIntExtra("platform", 0);
        int type = getIntent().getIntExtra("type", 0);
        if (platform == 0) {
            Toast.makeText(this, "platform is invalid !", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (type == 0) {
            Toast.makeText(this, "type is invalid !", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        if (platform == AppConstants.AD_FB) {
            adSdk = getAdSdkFb();
            switch (type) {
                case AppConstants.AD_SPLASH:
                    adSdk.showSplashAd(this, AidBox.testFbSplash());
                    break;

                case AppConstants.AD_NATIVE:
                    break;

                case AppConstants.AD_BANNER:
                    adSdk.showBannerAd((ViewGroup) findViewById(R.id.banner_container), AidBox.testFbBanner());
                    break;
            }

        } else {
            adSdk = getAdSdkAdmob();
            switch (type) {
                case AppConstants.AD_SPLASH:
                    adSdk.showSplashAd(this, AidBox.testAdmobSplash());
                    break;

                case AppConstants.AD_NATIVE:
                    adSdk.showNativeAd(findViewById(R.id.native_container), AidBox.testAdmobNative(), new NativeAd.AdListener() {
                        @Override
                        public void onSuccess(final View view, NativeAd ad) {
                            Log.d(TAG, "nativeAd success");
                            view.setVisibility(View.VISIBLE);
                            TextView tvTitle = view.findViewById(R.id.tv_title);
                            tvTitle.setText(ad.getTitle());
                            TextView tvContent = view.findViewById(R.id.tv_content);
                            tvContent.setText(ad.getContent());
                            ImageView ivIcon = view.findViewById(R.id.iv_icon);
                        }

                        @Override
                        public void onError(final View view, int code) {
                            Log.d(TAG, "nativeAd onError");

                            view.setVisibility(View.GONE);
                        }
                    });
                    break;

                case AppConstants.AD_BANNER:
                    adSdk.showBannerAd((ViewGroup) findViewById(R.id.banner_container), AidBox.testAdmobBanner());
                    break;
            }
        }
    }

    private AdSdk getAdSdkFb() {
        return ((DemoApp) getApplication()).getAdSdkTestFb();
    }

    private AdSdk getAdSdkAdmob() {
        return ((DemoApp) getApplication()).getAdSdkTestAdmob();
    }
}
