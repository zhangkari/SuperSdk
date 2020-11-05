package com.karic.sdk;

import com.karic.sdk.utils.AdUtils;
import com.karic.sdk.utils.Collections;

import java.util.List;

class AidBox {
    public String fbId;
    public String admobId;

    public AidBox(String fbId, String admobId) {
        this.fbId = fbId;
        this.admobId = admobId;
    }

    public static AidBox testAdmobBanner() {
        return new AidBox(null, AdUtils.TEST_ADMOB_BANNER_ID);
    }

    public static AidBox testAdmobSplash() {
        return new AidBox(null, AdUtils.TEST_ADMOB_SPLASH_ID);
    }

    public static AidBox testFbBanner() {
        return new AidBox(AdUtils.TEST_FB_BANNER_ID, null);
    }

    public static AidBox testFbSplash() {
        return new AidBox(AdUtils.TEST_FB_SPLASH_ID, null);
    }

    public static AidBox testAdmobNative() {
        return new AidBox(null, AdUtils.TEST_ADMOB_NATIVE_ID);
    }


    public static AidBox releaseSplash(SuperAdConfig config) {
        if (config.enableSplash) {
            return adaptAidBox(config.splashAd);
        }
        throw new RuntimeException("Please enable Splash Ad");
    }

    public static AidBox releaseBanner(SuperAdConfig config) {
        if (config.enableBanner) {
            return adaptAidBox(config.bannerAd);
        }
        throw new RuntimeException("Please enable Banner Ad");
    }

    public static AidBox releaseNative(SuperAdConfig config) {
        if (config.enableNative) {
            return adaptAidBox(config.nativeAd);
        }
        throw new RuntimeException("Please enable Native Ad");
    }

    private static AidBox adaptAidBox(List<SuperAdConfig.AdConfigItem> list) {
        if (Collections.isEmpty(list)) {
            throw new IllegalArgumentException("Invalid argument !");
        }

        String fbSplash = null;
        String admobSplash = null;
        for (SuperAdConfig.AdConfigItem item : list) {
            if (SuperAdConfig.PLATFORM_ADMOB.equalsIgnoreCase(item.platform)) {
                admobSplash = item.adPosition;
            } else if (SuperAdConfig.PLATFORM_FB.equalsIgnoreCase(item.platform)) {
                fbSplash = item.adPosition;
            }

            if (fbSplash != null && admobSplash != null) {
                break;
            }
        }
        if (fbSplash != null && admobSplash != null) {
            return new AidBox(fbSplash, admobSplash);
        }
        throw new IllegalArgumentException("Please config both Facebook and Admob Ad !");
    }
}
