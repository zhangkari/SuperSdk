package com.karic.sdk;

import android.content.Context;
import android.view.ViewGroup;

import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;

public final class AdSdk {

    private Settings settings;
    private SplashAd splashAd;
    private BannerAdView bannerAdView;

    AdSdk(Settings settings) {
        this.settings = settings;
    }

    public void init(Context context) {
        if (!settings.supportAdmob && !settings.supportFb) {
            return;
        }
        if (settings.supportAdmob) {
            if (!Utils.isValidAid(settings.admobAppId)) {
                throw new NullPointerException("Please set admob app id !");
            }
            initAdmob(context, settings.admobAppId);
        }

        if (settings.supportFb) {
            initFb(context);
        }
    }

    public void showSplashAd(Context context, AidBox aidBox) {
        if (splashAd != null) {
            splashAd.destroy();
        }
        splashAd = new SplashAd(context, settings, aidBox);
        splashAd.load();
    }

    public void destroySplashAd() {
        if (splashAd != null) {
            splashAd.destroy();
        }
    }

    public void showBannerAd(ViewGroup parent, AidBox aidBox) {
        if (bannerAdView != null) {
            bannerAdView.destroy();
        }
        bannerAdView = new BannerAdView(parent.getContext(), settings, aidBox);
        bannerAdView.load(parent);
    }

    public void resumeBannerAd() {
        if (bannerAdView != null) {
            bannerAdView.resume();
        }
    }

    public void pauseBannerAd() {
        if (bannerAdView != null) {
            bannerAdView.pause();
        }
    }

    public void destroyBannerAd() {
        if (bannerAdView != null) {
            bannerAdView.destroy();
        }
    }

    private void initAdmob(Context context, String admobAppId) {
        MobileAds.initialize(context, admobAppId);
    }

    private void initFb(Context context) {
        AudienceNetworkAds.initialize(context);
    }

    public static class Settings {
        boolean supportAdmob;
        boolean supportFb;
        boolean admobFirst;
        String admobAppId;

        public Settings() {
            supportAdmob = true;
            supportFb = true;
            admobFirst = false;
        }

        public Settings supportAdmob(boolean support) {
            supportAdmob = support;
            return this;
        }

        public Settings supportFb(boolean support) {
            supportFb = support;
            return this;
        }

        public Settings setAdmobFirst(boolean admobFirst) {
            this.admobFirst = admobFirst;
            return this;
        }

        public Settings setAdmobAppId(String admobAppId) {
            this.admobAppId = admobAppId;
            return this;
        }

        public AdSdk build() {
            return new AdSdk(this);
        }

        public static AdSdk testAdmobOnly() {
            Settings settings = new Settings();
            settings.supportFb(false)
                    .supportAdmob(true)
                    .setAdmobFirst(true)
                    .setAdmobAppId(Utils.TEST_ADMOB_APP_ID);
            return settings.build();
        }

        public static AdSdk testFbOnly() {
            Settings settings = new Settings();
            settings.supportFb(true)
                    .supportAdmob(false)
                    .setAdmobFirst(false);
            return settings.build();
        }
    }
}
