package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;

public class CacheManager {

    private ArrayList<UnifiedNativeAd> nativeAds = new ArrayList<>();

    private AdLoader adLoader;
    private AdLoader.Builder builder;
    private VideoOptions videoOptions;
    private NativeAdOptions adOptions;
    AdListener adListener;
    private long newAdRequestInterval = 600000;
    private  int numAdRequested;
    private long previousAdRequestTime;

    private String adUnitIDs;
    private Context mContext;


    public boolean isLoading() {

        if (adLoader != null) {
            return adLoader.isLoading();
        } else {
            return false;
        }
    }



    public int numberOfAds() {
        if (nativeAds != null) {
            return nativeAds.size();
        } else {
            return 0;
        }

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



    public void loadNativeAds(Context context, String adUnitID, int numOfAdsToLoad, int requestInterval) {

        newAdRequestInterval = (long)requestInterval;

        adUnitIDs = adUnitID;
        mContext = context;
        numAdRequested = numOfAdsToLoad;
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

            adLoader.loadAds(new AdRequest.Builder().build(),numOfAdsToLoad);

            previousAdRequestTime = System.currentTimeMillis();




        } catch (Exception e) {


        }
    }


    public UnifiedNativeAd getNativeAd(int index) {
        if ((System.currentTimeMillis() - previousAdRequestTime) > newAdRequestInterval) {

            loadNativeAds(mContext,adUnitIDs,numAdRequested, (int) newAdRequestInterval);
            return null;
        }

        if (nativeAds != null && nativeAds.size() != 0) {

            return nativeAds.get(index);

        } else {
            return  null;
        }


    }


}

