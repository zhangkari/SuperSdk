package com.karic.sdk;

public class AidBox {
    public String fbId;
    public String admobId;

    public AidBox(String fbId, String admobId) {
        this.fbId = fbId;
        this.admobId = admobId;
    }

    public static AidBox testAdmobBannerOnly() {
        return new AidBox(null, Utils.TEST_ADMOB_BANNER_ID);
    }

    public static AidBox testAdmobSplashOnly() {
        return new AidBox(null, Utils.TEST_ADMOB_SPLASH_ID);
    }

    public static AidBox testFbBannerOnly() {
        return new AidBox(Utils.TEST_FB_BANNER_ID, null);
    }

    public static AidBox testFbSplashOnly() {
        return new AidBox(Utils.TEST_FB_SPLASH_ID, null);
    }
}
