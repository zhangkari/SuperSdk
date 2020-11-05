package com.karic.sdk;

import android.content.Context;
import android.view.ViewGroup;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.karic.sdk.utils.Logger;

public class BannerAd extends SuperAd {

    private AdView admobView;
    private com.facebook.ads.AdView fbView;

    public BannerAd(Context context, AidBox box) {
        fbView = new com.facebook.ads.AdView(context, box.fbId, AdSize.BANNER_HEIGHT_50);
        admobView = new AdView(context);
        admobView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
        admobView.setAdUnitId(box.admobId);
    }

    public void load(ViewGroup parent) {
        load(parent, null);
    }

    public void load(ViewGroup parent, String pref) {
        load(parent, null, pref);
    }

    public void load(ViewGroup parent, SuperAdListener listener, String pref) {
        if (isAdmobPrefered(pref)) {
            loadAdmobAdView(parent, listener);
        } else {
            loadFbAdView(parent, listener);
        }
    }

    private void loadFbAdView(final ViewGroup parent, final SuperAdListener listener) {
        AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                Logger.d("Fb Banner Ad load failed, " + error.getErrorMessage());
                loadAdmobAdView(parent, listener);
                if (listener != null) {
                    listener.onAdLoadError(error.getErrorCode(), error.getErrorMessage());
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                Logger.d("Fb Banner Ad load success");

                if (fbView.getParent() != null) {
                    ((ViewGroup) fbView.getParent()).removeAllViews();
                }
                parent.addView(fbView);

                if (listener != null) {
                    listener.onAdLoaded();
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                if (listener != null) {
                    listener.onAdClicked();
                }
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        if (fbView.getParent() != null) {
            ((ViewGroup) fbView.getParent()).removeAllViews();
        }
        parent.addView(fbView);
        fbView.loadAd(
                fbView.buildLoadAdConfig()
                        .withAdListener(adListener)
                        .build()
        );
    }

    private void loadAdmobAdView(final ViewGroup parent, final SuperAdListener listener) {
        admobView.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                Logger.d("Admob Banner Ad load success");
                if (admobView.getParent() != null) {
                    ((ViewGroup) admobView.getParent()).removeAllViews();
                }
                parent.addView(admobView);
                if (listener != null) {
                    listener.onAdLoaded();
                }
            }

            @Override
            public void onAdFailedToLoad(LoadAdError error) {
                Logger.d("Admob Banner Ad load failed, " + error.getMessage());
                if (listener != null) {
                    listener.onAdLoadError(error.getCode(), error.getMessage());
                }
                loadFbAdView(parent, listener);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                if (listener != null) {
                    listener.onAdClicked();
                }
            }
        });

        if (admobView.getParent() != null) {
            ((ViewGroup) admobView.getParent()).removeAllViews();
        }
        parent.addView(admobView);
        admobView.loadAd(new AdRequest.Builder().build());
    }

    public void destroy() {
        if (fbView != null) {
            fbView.destroy();
        }
        if (admobView != null) {
            admobView.setAdListener(null);
            admobView.destroy();
        }
    }

    public void resume() {
        if (admobView != null && admobView.getParent() != null) {
            admobView.resume();
        }
    }

    public void pause() {
        if (admobView != null && admobView.getParent() != null) {
            admobView.pause();
        }
    }
}
