package com.karic.sdk;

import android.content.Context;
import android.util.Log;

import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class SplashAd {
    private static final String TAG = "SplashAd";
    private AdSdk.Settings settings;
    private InterstitialAd admobAd;
    private com.facebook.ads.InterstitialAd fbAd;

    public SplashAd(Context context, AdSdk.Settings settings, AidBox box) {
        this.settings = settings;
        if (settings.supportAdmob && Utils.isValidAid(box.admobId)) {
            admobAd = new InterstitialAd(context);
            admobAd.setAdUnitId(box.admobId);
        }
        if (settings.supportFb && Utils.isValidAid(box.fbId)) {
            fbAd = new com.facebook.ads.InterstitialAd(context, box.fbId);
        }
    }

    public void load() {
        load(null, true);
    }

    public void load(boolean autoShow) {
        load(null, autoShow);
    }

    public void load(AdListener listener, boolean autoShow) {
        if (settings.admobFirst) {
            if (admobAd != null) {
                setAdmobAdListener(admobAd, true, listener, autoShow);
            } else if (fbAd != null) {
                setFbAdListener(fbAd, true, listener, autoShow);
            }
        } else {
            if (fbAd != null) {
                setFbAdListener(fbAd, true, listener, autoShow);
            } else if (admobAd != null) {
                setAdmobAdListener(admobAd, true, listener, autoShow);
            }
        }
    }

    private void setAdmobAdListener(final InterstitialAd ad, final boolean hasNext, final AdListener listener, final boolean autoShow) {
        ad.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdFailedToLoad(int code) {
                Log.d(TAG, "admob load failed, code = " + code + ", msg = " + Utils.formatAdmobError(code));
                if (hasNext) {
                    if (fbAd != null) {
                        setFbAdListener(fbAd, false, listener, autoShow);
                    }
                } else {
                    if (listener != null) {
                        listener.onAdLoadError(code, Utils.formatAdmobError(code));
                    }
                }
            }

            @Override
            public void onAdLoaded() {
                if (autoShow) {
                    admobAd.show();
                }
                if (listener != null) {
                    listener.onAdLoaded();
                }
            }

            @Override
            public void onAdClicked() {
                if (listener != null) {
                    listener.onAdClicked();
                }
            }
        });
        ad.loadAd(new AdRequest.Builder().build());
    }

    private void setFbAdListener(com.facebook.ads.InterstitialAd ad, final boolean hasNext, final AdListener listener, final boolean autoShow) {
        ad.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(com.facebook.ads.Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(com.facebook.ads.Ad ad) {

            }

            @Override
            public void onError(com.facebook.ads.Ad ad, AdError adError) {
                Log.d(TAG, "fb load Failed, code = " + adError.getErrorCode() + ", msg = " + adError.getErrorMessage());
                if (hasNext) {
                    if (admobAd != null) {
                        setAdmobAdListener(admobAd, false, listener, autoShow);
                    }
                } else {
                    if (listener != null) {
                        listener.onAdLoadError(adError.getErrorCode(), adError.getErrorMessage());
                    }
                }
            }

            @Override
            public void onAdLoaded(com.facebook.ads.Ad ad) {
                if (autoShow) {
                    fbAd.show();
                }
                if (listener != null) {
                    listener.onAdLoaded();
                }
            }

            @Override
            public void onAdClicked(com.facebook.ads.Ad ad) {
                if (listener != null) {
                    listener.onAdClicked();
                }
            }

            @Override
            public void onLoggingImpression(com.facebook.ads.Ad ad) {

            }
        });
        fbAd.loadAd();
    }

    public void show() {
        if (settings.admobFirst) {
            if (admobAd != null && admobAd.isLoaded()) {
                admobAd.show();
            } else if (fbAd != null && fbAd.isAdLoaded() && !fbAd.isAdInvalidated()) {
                fbAd.show();
            }
        } else {
            if (fbAd != null && fbAd.isAdLoaded() && !fbAd.isAdInvalidated()) {
                fbAd.show();
            } else if (admobAd != null && admobAd.isLoaded()) {
                admobAd.show();
            }
        }
    }

    public void destroy() {
        if (admobAd != null) {
            admobAd.setAdListener(null);
            admobAd = null;
        }
        if (fbAd != null) {
            fbAd.setAdListener(null);
            fbAd.destroy();
            fbAd = null;
        }
    }
}
