package com.karic.sdk.sdkdemo;

import androidx.annotation.IntDef;

final class AppConstants {
    public static final int AD_ADMOB = 1000;
    public static final int AD_FB = 2000;

    public static final int AD_BANNER = 8000;
    public static final int AD_SPLASH = 8001;
    public static final int AD_NATIVE = 8002;

    @IntDef({AD_ADMOB, AD_FB})
    @interface AdPlatform {

    }

    @IntDef({AD_BANNER, AD_SPLASH, AD_NATIVE})
    @interface AdType {

    }
}
