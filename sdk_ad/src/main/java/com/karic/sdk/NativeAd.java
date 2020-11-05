package com.karic.sdk;

import android.view.View;

import com.google.android.gms.ads.formats.UnifiedNativeAd;

public class NativeAd {
    private UnifiedNativeAd nativeAd;

    public NativeAd() {
    }

    public String getTitle() {
        return nativeAd.getHeadline();
    }

    public String getContent() {
        return nativeAd.getBody();
    }

    public void destroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
    }

    public interface AdListener {
        void onSuccess(View view, NativeAd ad);

        void onError(View view, int code);
    }
}
