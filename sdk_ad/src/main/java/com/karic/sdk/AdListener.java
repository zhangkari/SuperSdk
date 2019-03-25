package com.karic.sdk;

public interface AdListener {
    void onAdLoaded();

    void onAdLoadError(int code, String message);

    void onAdClicked();
}
