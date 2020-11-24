package com.ammarahmed.rnadmob.nativeads;

import android.os.Bundle;

import com.facebook.react.bridge.ReactContext;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.Random;

public class RNAdMobPreloader {

    private ArrayList<UnifiedNativeAd> nativeAds = new ArrayList<>();
    private ArrayList<UnifiedNativeAd> nativeVideoAds = new ArrayList<>();

    private AdLoader adLoader;
    private AdLoader.Builder builder;
    private VideoOptions videoOptions;
    private NativeAdOptions adOptions;
    AdListener adListener;
    public String adUnitID;
    public String videoAdUnitID;

    private ReactContext mContext;
    private int numberOfAdsToLoad = 0;
    private int numberOfVideoAdsToLoad = 0;
    public boolean requestNonPersonalizedAdsOnly = false;
    public int mediaAspectRatio = 1;
    public int adChoicesPlacement = 1;
    public boolean videosStartMuted = true;

    public void setMediaAspectRatio(int aspectRatio) {
        mediaAspectRatio = aspectRatio;
    }

    public void setRequestNonPersonalizedAdsOnly(boolean npa) {
        requestNonPersonalizedAdsOnly = npa;
    }

    public void setAdUnitIDs(String id, String vId) {
        adUnitID = id;
        videoAdUnitID = vId;
    }

    public void setNumberOfAdsToLoad(int numberOfAdsToLoad, int numberOfVideoAdsToLoad) {
        this.numberOfAdsToLoad = numberOfAdsToLoad;
        this.numberOfVideoAdsToLoad = numberOfVideoAdsToLoad;
    }

    public void setAdChoicesPlacement(int adChoicesPlacement) {
        this.adChoicesPlacement = adChoicesPlacement;
    }

    public void setVideosStartMuted(boolean videosStartMuted) {
        this.videosStartMuted = videosStartMuted;
    }


    public int numberOfAds() {
        if (nativeAds == null) {
            return 0;

        }
        return nativeAds.size();
    }


    public void attachAdListener(AdListener listener) {
        adListener = listener;
    }

    UnifiedNativeAd.OnUnifiedNativeAdLoadedListener onUnifiedNativeAdLoadedListener = new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
        @Override
        public void onUnifiedNativeAdLoaded(UnifiedNativeAd nativeAd) {
            if (nativeAd.getMediaContent().hasVideoContent()) {
                nativeVideoAds.add(nativeAd);
            } else {
                nativeAds.add(nativeAd);
            }
        }
    };

    /**
     * Since we will preload the ads, AdMob has no
     * way to attach separate listeners to each Ad View after,
     * the AdRequest has returned an Ad so we will use a single
     * AdListener that will send events to JS using the AdManager.
     */

    AdListener adListen = new AdListener() {

        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            if (adListener == null) return;
            adListener.onAdFailedToLoad(i);
        }

        @Override
        public void onAdFailedToLoad(LoadAdError loadAdError) {
            super.onAdFailedToLoad(loadAdError);
            if (adListener == null) return;
            adListener.onAdFailedToLoad(loadAdError);
        }

        @Override
        public void onAdClosed() {
            super.onAdClosed();
            if (adListener == null) return;
            adListener.onAdClosed();
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
            if (adListener == null) return;
            adListener.onAdOpened();
        }

        @Override
        public void onAdClicked() {
            super.onAdClicked();
            if (adListener == null) return;
            adListener.onAdClicked();

        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();

            if (adListener == null) return;
            if (nativeAds.size() == 1) {
                adListener.onAdLoaded();
                return;
            }
        }

        @Override
        public void onAdImpression() {
            super.onAdImpression();
            if (adListener == null) return;
            adListener.onAdImpression();
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
            if (adListener == null) return;
            adListener.onAdLeftApplication();
        }
    };


    /**
     * @param context The Context using which the ads will be loaded.
     */

    public void loadNativeAds(ReactContext context) {
        if (adUnitID != null && numberOfAdsToLoad > 0) {
            mContext = context;
            nativeAds.clear();
            preloadAds(adUnitID);
        } else {
            nativeAds.clear();
        }
    }

    private void preloadAds(String adId) {
        try {
            builder = new AdLoader.Builder(mContext, adId);
            builder.forUnifiedNativeAd(onUnifiedNativeAdLoadedListener);
            videoOptions = new VideoOptions.Builder()
                    .setStartMuted(videosStartMuted)
                    .build();
            adOptions = new NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions)
                    .setAdChoicesPlacement(adChoicesPlacement)
                    .setMediaAspectRatio(mediaAspectRatio)
                    .build();

            builder.withNativeAdOptions(adOptions);

            adLoader = builder.withAdListener(adListen).build();
            AdRequest adRequest;

            if (requestNonPersonalizedAdsOnly) {
                Bundle extras = new Bundle();
                extras.putString("npa", "1");
                adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();

            } else {
                adRequest = new AdRequest.Builder().build();
            }

            adLoader.loadAds(adRequest, numberOfAdsToLoad);


        } catch (Exception e) {
        }
    }

    public void loadNativeVideoAds(ReactContext context) {
        if (videoAdUnitID != null && numberOfVideoAdsToLoad > 0) {
            mContext = context;
            nativeVideoAds.clear();
            preloadAds(videoAdUnitID);
        } else {
            nativeVideoAds.clear();
        }
    }


    /**
     * This method is used to get a random ad from the list of ads.
     *
     * @return UnifiedNativeAd
     */

    public UnifiedNativeAd getNativeAd() {
        if ((nativeAds == null) || (nativeAds.size() == 0)) return null;
        Random random = new Random();
        int randomNumber = random.nextInt(nativeAds.size() - 0) + 0;
        return nativeAds.get(randomNumber);
    }

    /**
     * This method is used to get a random ad from the list of ads.
     *
     * @return UnifiedNativeAd
     */

    public UnifiedNativeAd getNativeVideoAds() {
        if ((nativeVideoAds == null) || (nativeVideoAds.size() == 0)) return null;
        Random random = new Random();
        int randomNumber = random.nextInt(nativeVideoAds.size() - 0) + 0;
        return nativeVideoAds.get(randomNumber);
    }
}