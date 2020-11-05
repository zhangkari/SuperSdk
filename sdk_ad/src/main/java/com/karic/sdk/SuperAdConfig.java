package com.karic.sdk;

import java.util.List;

public final class SuperAdConfig {
    public static final String PLATFORM_ADMOB = "admob";
    public static final String PLATFORM_FB = "fb";

    public boolean enableSplash;
    public boolean enableBanner;
    public boolean enableNative;

    public String splashPrefer;
    public String bannerPrefer;
    public String nativePrefer;

    public List<AdConfigItem> splashAd;
    public List<AdConfigItem> bannerAd;
    public List<AdConfigItem> nativeAd;

    public static class AdConfigItem {
        public String platform;
        public String adPosition;
    }
}
