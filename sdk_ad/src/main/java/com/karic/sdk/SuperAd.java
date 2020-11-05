package com.karic.sdk;

abstract class SuperAd {
    protected boolean isAdmobPrefered(String prefer) {
        return prefer == null || prefer.isEmpty() || SuperAdConfig.PLATFORM_ADMOB.equalsIgnoreCase(prefer);
    }
}
