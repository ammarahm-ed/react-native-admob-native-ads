package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.os.Bundle;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class RNAdMobUnifiedAdQueueWrapper {

    public String adUnitId;
    public String name;
    public Boolean npa = true;
    public Integer totalAds = 5;
    public long expirationInterval = 3600000; // in ms
    public Boolean muted = true;
    public Boolean mediation = false;
    private final AdLoader adLoader;
    private AdRequest adRequest;
    AdListener attachedAdListener;
    private final onUnifiedNativeAdLoadedListener unifiedNativeAdLoadedListener;
    public List<RNAdMobUnifiedAdContainer> nativeAds;
    Context mContext;

    public void attachAdListener(AdListener listener) {
        attachedAdListener = listener;
    }

    public void detachAdListener() {
        attachedAdListener = null;
    }

    public RNAdMobUnifiedAdQueueWrapper(Context context, ReadableMap config, String repo) {
        mContext = context;
        adUnitId = config.getString("adUnitId");
        name = repo;

        if (config.hasKey("numOfAds")) {
            totalAds = config.getInt("numOfAds");
        }
        nativeAds = new ArrayList<RNAdMobUnifiedAdContainer>(totalAds);
        if (config.hasKey("mute")) {
            muted = config.getBoolean("mute");
        }
        if (config.hasKey("expirationPeriod")) {
            expirationInterval = config.getInt("expirationPeriod");
        }
        if (config.hasKey("mediationEnabled")) {
            mediation = config.getBoolean("mediationEnabled");
        }
        if (config.hasKey("nonPersonalizedAdsOnly")) {
            npa = config.getBoolean("nonPersonalizedAdsOnly");
            Bundle extras = new Bundle();
            extras.putString("npa", npa ? "1" : "0");
            adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();
        } else {
            adRequest = new AdRequest.Builder().build();
        }
        unifiedNativeAdLoadedListener = new onUnifiedNativeAdLoadedListener(repo, nativeAds,
                totalAds, context);
        AdLoader.Builder builder = new AdLoader.Builder(context, adUnitId);
        builder.forNativeAd(unifiedNativeAdLoadedListener);
        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(muted)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT) // todo:: get from config
                .build();
        builder.withNativeAdOptions(adOptions);

        AdListener adListener = new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                super.onAdFailedToLoad(adError);
                boolean stopPreloading = false;
                switch (adError.getCode()) {
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        stopPreloading = true;
                }

                if (attachedAdListener == null) {
                    if (stopPreloading) {
                        WritableMap event = Arguments.createMap();
                        WritableMap error = Arguments.createMap();
                        error.putString("message", adError.getMessage());
                        error.putInt("code", adError.getCode());
                        error.putString("domain", adError.getDomain());
                        event.putMap("error", error);
                        EventEmitter.sendEvent((ReactContext) mContext, CacheManager.EVENT_AD_PRELOAD_ERROR, event);
                    }
                    return;
                }
                attachedAdListener.onAdFailedToLoad(adError);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                if (attachedAdListener == null) return;
                attachedAdListener.onAdClosed();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                if (attachedAdListener == null) return;
                attachedAdListener.onAdOpened();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                if (attachedAdListener == null) return;
                attachedAdListener.onAdClicked();

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (attachedAdListener == null) return;
                attachedAdListener.onAdLoaded();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                if (attachedAdListener == null) return;
                attachedAdListener.onAdImpression();
            }
        };
        adLoader = builder.withAdListener(adListener).build();
    }

    public void loadAds() {
        if (mediation) {
            for (int i = 0; i < totalAds; i++) {
                adLoader.loadAd(adRequest);
            }
        } else {
            adLoader.loadAds(adRequest, totalAds);
        }
    }

    public void loadAd() {
        adLoader.loadAd(adRequest);
        fillAd();
    }

    public void fillAd() {
        if (!isLoading()) {
            adLoader.loadAd(adRequest);
        }
    }

    public RNAdMobUnifiedAdContainer getAd() {
        long now = System.currentTimeMillis();
        RNAdMobUnifiedAdContainer ad = null;

        if (!nativeAds.isEmpty()) {
            Collections.sort(nativeAds, new RNAdMobUnifiedAdComparator());
            List<RNAdMobUnifiedAdContainer> discardItems = new ArrayList<>();
            for (RNAdMobUnifiedAdContainer item : nativeAds) {
                if ((now - item.loadTime) < expirationInterval) {
                    ad = item;//acceptable ad found
                    break;
                } else {
                    if (item.references <= 0) {
                        discardItems.add(item);
                    }
                }
            }
            for (RNAdMobUnifiedAdContainer item : discardItems) {
                item.unifiedNativeAd.destroy();
                nativeAds.remove(item);
            }
        } else {
            return null;
        }

        assert ad != null;
        ad.showCount += 1;
        ad.references += 1;
        fillAd();
        return ad;
    }

    public Boolean isLoading() {
        if (adLoader != null) {
            return adLoader.isLoading();
        }
        return false;
    }

    public WritableMap hasAd() {
        WritableMap args = Arguments.createMap();
        args.putInt(name, nativeAds.size());
        return args;
    }
}
