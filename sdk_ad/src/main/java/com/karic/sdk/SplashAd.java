package com.karic.sdk;

import android.content.Context;

import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.karic.sdk.utils.AdUtils;
import com.karic.sdk.utils.Logger;

public class SplashAd extends SuperAd {
    private InterstitialAd admobAd;
    private com.facebook.ads.InterstitialAd fbAd;

    public SplashAd(Context context, AidBox box) {
        admobAd = new InterstitialAd(context);
        admobAd.setAdUnitId(box.admobId);
        fbAd = new com.facebook.ads.InterstitialAd(context, box.fbId);
    }

    public void load() {
        load(null, false, null);
    }

    public void load(boolean autoShow, String prefer) {
        load(null, autoShow, prefer);
    }

    public void load(final SuperAdListener listener, final boolean autoShow, String prefer) {
        if (isAdmobPrefered(prefer)) {
            loadAdmobAd(listener, autoShow);
        } else {
            loadFbAd(listener, autoShow);
        }
    }


    private void loadAdmobAd(final SuperAdListener listener, final boolean autoShow) {
        admobAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdClosed() {
                Logger.d("Admob Splash Ad onAdClosed");
                loadAdmobAd(listener, autoShow);
            }

            @Override
            public void onAdFailedToLoad(int code) {
                Logger.d("Admob Splash Ad load failed, code = " + code + ", msg = " + AdUtils.formatAdmobError(code));
                if (listener != null) {
                    listener.onAdLoadError(code, AdUtils.formatAdmobError(code));
                }
                loadFbAd(listener, autoShow);
            }

            @Override
            public void onAdLoaded() {
                Logger.d("Admob Splash Ad load success");

                if (autoShow) {
                    admobAd.show();
                }
                if (listener != null) {
                    listener.onAdLoaded();
                }
            }

            @Override
            public void onAdClicked() {
                Logger.d("Admob Splash Ad onAdClicked");

                if (listener != null) {
                    listener.onAdClicked();
                }
            }
        });
        admobAd.loadAd(new AdRequest.Builder().build());
    }

    private void loadFbAd(final SuperAdListener listener, final boolean autoShow) {
        InterstitialAdListener adListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {
                Logger.d("FB Splash Ad onInterstitialDisplayed");
            }

            @Override
            public void onInterstitialDismissed(com.facebook.ads.Ad ad) {
                Logger.d("FB Splash Ad onInterstitialDismissed");
                loadFbAd(listener, autoShow);
            }

            @Override
            public void onError(com.facebook.ads.Ad ad, AdError adError) {
                Logger.d("FB Splash Ad load failed, code = " + adError.getErrorCode() + ", msg = " + adError.getErrorMessage());
                if (listener != null) {
                    listener.onAdLoadError(adError.getErrorCode(), adError.getErrorMessage());
                }
                loadAdmobAd(listener, autoShow);
            }

            @Override
            public void onAdLoaded(com.facebook.ads.Ad ad) {
                Logger.d("FB Splash Ad load success");
                if (autoShow) {
                    fbAd.show();
                }
                if (listener != null) {
                    listener.onAdLoaded();
                }
            }

            @Override
            public void onAdClicked(com.facebook.ads.Ad ad) {
                Logger.d("FB Splash Ad onAdClicked");
                if (listener != null) {
                    listener.onAdClicked();
                }
            }

            @Override
            public void onLoggingImpression(com.facebook.ads.Ad ad) {
                Logger.d("FB Splash Ad onLoggingImpression");
            }
        };
        fbAd.loadAd(
                fbAd.buildLoadAdConfig()
                        .withAdListener(adListener)
                        .build()
        );
    }

    public void show() {
        if (admobAd != null && admobAd.isLoaded()) {
            admobAd.show();
        } else if (fbAd != null && fbAd.isAdLoaded() && !fbAd.isAdInvalidated()) {
            fbAd.show();
        }
    }

    public void destroy() {
        if (admobAd != null) {
            admobAd.setAdListener(null);
            admobAd = null;
        }
        if (fbAd != null) {
            fbAd.destroy();
            fbAd = null;
        }
    }
}
