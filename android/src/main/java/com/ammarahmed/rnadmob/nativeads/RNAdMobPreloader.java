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

    private AdLoader adLoader;
    private AdLoader.Builder builder;
    private VideoOptions videoOptions;
    private NativeAdOptions adOptions;
    AdListener adListener;
    private int numAdRequested;
    private String adUnitID;
    private ReactContext mContext;


    public boolean isLoading() {

        if (adLoader == null) {
            return false;
        }

        return adLoader.isLoading();

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

            nativeAds.add(nativeAd);

        }
    };


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
     * @param context        The Context in which the ads will be loaded.
     * @param id             The adUnitID that will be used to request ads.
     * @param numOfAdsToLoad Number of ads to load (Max:5).
     * @param npa            Show non-personalized ads
     */

    public void loadNativeAds(ReactContext context, String id, int numOfAdsToLoad, boolean npa) {

        adUnitID = id;
        mContext = context;
        numAdRequested = numOfAdsToLoad;
        nativeAds.clear();
        try {
            builder = new AdLoader.Builder(context, adUnitID);
            builder.forUnifiedNativeAd(onUnifiedNativeAdLoadedListener);
            videoOptions = new VideoOptions.Builder()
                    .setStartMuted(true)
                    .build();
            adOptions = new NativeAdOptions.Builder()
                    .setVideoOptions(videoOptions)
                    .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                    .build();

            builder.withNativeAdOptions(adOptions);

            adLoader = builder.withAdListener(adListen).build();
            AdRequest adRequest;

            if (npa) {
                Bundle extras = new Bundle();
                extras.putString("npa", "1");
                adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();

            } else {
                adRequest = new AdRequest.Builder().build();
            }

            adLoader.loadAds(adRequest, numOfAdsToLoad);


        } catch (Exception e) {}
    }


    /**
     * This method is used to get a random ad from the list of ads.
     *
     * @param index Index of the ad to load.
     * @return UnifiedNativeAd
     */

    public UnifiedNativeAd getNativeAd(int index) {
        if ((nativeAds == null) || (nativeAds.size() == 0)) return null;
        Random random = new Random();
        int randomNumber = random.nextInt(nativeAds.size() - 0) + 0;
        return nativeAds.get(randomNumber);
    }
}