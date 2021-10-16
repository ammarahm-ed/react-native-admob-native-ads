package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RNAdMobUnifiedAdQueueWrapper {

    public String adUnitId;
    public String name;
    public Integer totalAds = 5;
    public long expirationInterval = 3600000; // in ms
    public Boolean muted = true;
    public Boolean mediation = false;
    public List<RNAdMobUnifiedAdContainer> nativeAds;
    AdListener attachedAdListener;
    Context mContext;
    VideoOptions.Builder videoOptions;
    NativeAdOptions.Builder adOptions;
    AdListener adListener;
    private AdLoader adLoader;
    private AdManagerAdRequest.Builder adRequest;
    private onUnifiedNativeAdLoadedListener unifiedNativeAdLoadedListener;

    public RNAdMobUnifiedAdQueueWrapper(Context context, ReadableMap config, String repository) {
        mContext = context;
        adUnitId = config.getString("adUnitId");
        name = repository;
        videoOptions = new VideoOptions.Builder();
        adRequest = new AdManagerAdRequest.Builder();
        adOptions = new NativeAdOptions.Builder();

        adListener = new AdListener() {
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

        setConfiguration(config);

    }

    public void attachAdListener(AdListener listener) {
        attachedAdListener = listener;
    }

    public void detachAdListener() {
        attachedAdListener = null;
    }

    public void setConfiguration(ReadableMap config) {
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

        if (config.hasKey("adChoicesPlacement")) {
            adOptions.setAdChoicesPlacement(config.getInt("adChoicesPlacement"));
        }

        if (config.hasKey("requestNonPersonalizedAdsOnly")) {
            Utils.setRequestNonPersonalizedAdsOnly(config.getBoolean("requestNonPersonalizedAdsOnly"), adRequest);
        }
        ;

        if (config.hasKey("mediaAspectRatio")) {
            Utils.setMediaAspectRatio(config.getInt("mediaAspectRatio"), adOptions);
        }

        Utils.setVideoOptions(config.getMap("videoOptions"), videoOptions, adOptions);
        Utils.setTargetingOptions(config.getMap("targetingOptions"), adRequest);
        Utils.setMediationOptions(config.getMap("mediationOptions"), adRequest);

        unifiedNativeAdLoadedListener = new onUnifiedNativeAdLoadedListener(name, nativeAds,
                totalAds, mContext);
        AdLoader.Builder builder = new AdLoader.Builder(mContext, adUnitId);
        builder.forNativeAd(unifiedNativeAdLoadedListener);
        adLoader = builder.withAdListener(adListener).build();
    }

    public void loadAds() {
        if (mediation) {
            for (int i = 0; i < totalAds; i++) {
                adLoader.loadAd(adRequest.build());
            }
        } else {
            adLoader.loadAds(adRequest.build(), totalAds);
        }
    }

    public void loadAd() {
        adLoader.loadAd(adRequest.build());
        fillAd();
    }

    public void fillAd() {
        if (!isLoading()) {
            adLoader.loadAd(adRequest.build());
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
