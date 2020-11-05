package com.karic.sdk;

public interface SuperAdListener {
    void onAdLoaded();

    void onAdLoadError(int code, String message);

    void onAdClicked();
}
