package com.karic.sdk;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.ads.AdSettings;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.karic.sdk.sdk_ad.BuildConfig;
import com.karic.sdk.utils.Assets;
import com.karic.sdk.utils.JSONs;
import com.karic.sdk.utils.Logger;

public class SuperAdSdk {
    private SuperAdConfig adConfig;
    private static SuperAdSdk _instance;

    private SplashAd splashAd;
    private BannerAd bannerAd;
    private NativeAd nativeAd;

    public static void init(Context context, String path) {
        if (_instance == null) {
            synchronized (SuperAdSdk.class) {
                if (_instance == null) {
                    _instance = new SuperAdSdk(context, path);
                }
            }
        }
    }

    public static SuperAdSdk getInstance() {
        if (_instance == null) {
            throw new RuntimeException("please init first !");
        }
        return _instance;
    }

    private void initAdmobSdk(Context context) {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus status) {
                Logger.d("MobileAds init complete");
            }
        });
    }

    private void initFacebookSdk(Context context) {
        if (!AudienceNetworkAds.isInitialized(context)) {
            if (BuildConfig.DEBUG) {
                AdSettings.turnOnSDKDebugger(context);
            }
            AudienceNetworkAds
                    .buildInitSettings(context)
                    .withInitListener(new AudienceNetworkAds.InitListener() {
                        @Override
                        public void onInitialized(AudienceNetworkAds.InitResult result) {
                            Logger.d("Facebook Ads init " + result.isSuccess() + "," + result.getMessage());
                        }
                    })
                    .initialize();
        }
    }

    private SuperAdSdk(Context context, String path) {
        adConfig = JSONs.fromJson(Assets.loadText(context.getAssets(), path), SuperAdConfig.class);
        initAdmobSdk(context);
        initFacebookSdk(context);
        if (adConfig.enableSplash) {
            splashAd = new SplashAd(context, AidBox.releaseSplash(adConfig));
        }

        if (adConfig.enableBanner) {
            bannerAd = new BannerAd(context, AidBox.releaseBanner(adConfig));
        }

        if (adConfig.enableNative) {
            nativeAd = new NativeAd();
        }
    }

    public void loadSplashAd(boolean autoShow) {
        if (adConfig.enableSplash) {
            splashAd.load(autoShow, adConfig.splashPrefer);
        }
    }

    public void showSplashAd() {
        if (adConfig.enableSplash) {
            splashAd.show();
        }
    }

    void releaseSplashAd() {
        if (adConfig.enableSplash) {
            splashAd.destroy();
        }
    }

    public void showBannerAd(View parent) {
        if (adConfig.enableBanner) {
            if (parent instanceof ViewGroup) {
                bannerAd.load((ViewGroup) parent, adConfig.bannerPrefer);
            } else {
                throw new RuntimeException("Banner Container must be ViewGroup !");
            }
        }
    }

    public void resumeBannerAd() {
        if (adConfig.enableBanner) {
            bannerAd.resume();
        }
    }

    public void pauseBannerAd() {
        if (adConfig.enableBanner) {
            bannerAd.pause();
        }
    }

    void destroyBannerAd() {
        if (adConfig.enableBanner) {
            bannerAd.destroy();
        }
    }
}
