package com.karic.sdk;

import android.content.Context;
import android.view.ViewGroup;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class BannerAdView {
    private AdSdk.Settings settings;

    private AdView admobView;
    private com.facebook.ads.AdView fbView;

    public BannerAdView(Context context, AdSdk.Settings settings, AidBox box) {
        this.settings = settings;
        if (settings.supportFb && Utils.isValidAid(box.fbId)) {
            fbView = new com.facebook.ads.AdView(context, box.fbId, AdSize.BANNER_HEIGHT_50);
        }
        if (settings.supportAdmob && Utils.isValidAid(box.admobId)) {
            admobView = new AdView(context);
            admobView.setAdSize(com.google.android.gms.ads.AdSize.LARGE_BANNER);
            admobView.setAdUnitId(box.admobId);
        }
    }

    public void load(ViewGroup parent) {
        load(parent, null);
    }

    public void load(ViewGroup parent, AdListener listener) {
        if (settings.admobFirst) {
            if (settings.supportAdmob && admobView != null) {
                loadAdmobAdView(admobView, parent, true, listener);
            } else if (settings.supportFb && fbView != null) {
                loadFbAdView(fbView, parent, true, listener);
            }
        } else {
            if (settings.supportFb && fbView != null) {
                loadFbAdView(fbView, parent, true, listener);
            } else if (settings.supportAdmob && admobView != null) {
                loadAdmobAdView(admobView, parent, true, listener);
            }
        }
    }

    private void loadFbAdView(final com.facebook.ads.AdView view, final ViewGroup parent, final boolean hasNext, final AdListener listener) {
        view.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                if (hasNext) {
                    if (admobView != null) {
                        loadAdmobAdView(admobView, parent, false, listener);
                    }
                } else {
                    if (listener != null) {
                        listener.onAdLoadError(adError.getErrorCode(), adError.getErrorMessage());
                    }
                }
            }

            @Override
            public void onAdLoaded(Ad ad) {
                parent.removeAllViews();
                parent.addView(view);
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
        });
        view.loadAd();
    }

    private void loadAdmobAdView(final AdView view, final ViewGroup parent, final boolean hasNext, final AdListener listener) {
        view.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                parent.removeAllViews();
                parent.addView(view);
                if (listener != null) {
                    listener.onAdLoaded();
                }
            }

            @Override
            public void onAdFailedToLoad(int code) {
                super.onAdFailedToLoad(code);
                if (hasNext) {
                    loadFbAdView(fbView, parent, false, listener);
                } else {
                    if (listener != null) {
                        listener.onAdLoadError(code, Utils.formatAdmobError(code));
                    }
                }
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                if (listener != null) {
                    listener.onAdClicked();
                }
            }
        });
        view.loadAd(new AdRequest.Builder().build());
    }

    public void destroy() {
        if (fbView != null) {
            fbView.setAdListener(null);
            fbView.destroy();
        }
        if (admobView != null) {
            admobView.setAdListener(null);
            admobView.destroy();
        }
    }

    public void resume() {
        if (admobView != null) {
            admobView.resume();
        }
    }

    public void pause() {
        if (admobView != null) {
            admobView.pause();
        }
    }
}
