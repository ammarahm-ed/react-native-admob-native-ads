package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.lang.Long;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class RNAdMobUnifiedAdWrapper {

    public String adUnitId;
    public Boolean npa = true;
    public Integer totalAds = 5;
    public long expirationInterval = 60*60*1000; // in ms
    public Boolean muted = true;
    private final AdLoader adLoader;
    private AdRequest adRequest;
    private final onUnifiedNativeAdLoadedListener unifiedNativeAdLoadedListener;
    AdListener attachedAdListener;
    public Stack<Pair<Long, UnifiedNativeAd>> mutedAds= new Stack<>(); // every entry is => time of loading => ad loaded
    public Stack<Pair<Long, UnifiedNativeAd>> unMutedAds= new Stack<>(); // every entry is => time of loading => ad loaded
    public Map<Boolean, Stack<Pair<Long, UnifiedNativeAd>>> nativeAdsMap = new HashMap<>();

    private final AdListener adListener = new AdListener() {
        @Override
        public void onAdFailedToLoad(int i) {
            super.onAdFailedToLoad(i);
            System.out.println("younes ad failed to load " + i);
            if (attachedAdListener == null) return;
            attachedAdListener.onAdFailedToLoad(i);
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
            System.out.println("younes ad loadeddddd ");
            if (attachedAdListener == null) return;
            attachedAdListener.onAdLoaded();
//            if (nativeAds.size() == 1) {
//                attachedAdListener.onAdLoaded();
//            }
        }

        @Override
        public void onAdImpression() {
            super.onAdImpression();
            if (attachedAdListener == null) return;
            attachedAdListener.onAdImpression();
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
            if (attachedAdListener == null) return;
            attachedAdListener.onAdLeftApplication();
        }
    };

    public void attachAdListener(AdListener listener) {
        attachedAdListener = listener;
    }

    public void detachAdListener() {
        attachedAdListener = null;
    }

    public RNAdMobUnifiedAdWrapper(Context context, ReadableMap config){
        adUnitId = config.getString("adUnitId");
        nativeAdsMap.put(true, mutedAds);
        nativeAdsMap.put(false, unMutedAds);

        System.out.println("younes start loading for "+adUnitId);

        if (config.hasKey("numOfAds")){
            totalAds = config.getInt("numOfAds");
        }
        if (config.hasKey("mute")){
            muted = config.getBoolean("mute");
        }
        if (config.hasKey("requestNonPersonalizedAdsOnly")) {
            npa = config.getBoolean("requestNonPersonalizedAdsOnly");
            if (config.hasKey("requestNonPersonalizedAdsOnly")) {
                Bundle extras = new Bundle();
                extras.putString("npa", config.getBoolean("requestNonPersonalizedAdsOnly") ? "1" : "0");
                adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();
            } else {
                adRequest = new AdRequest.Builder().build();
            }
        }
        unifiedNativeAdLoadedListener = new onUnifiedNativeAdLoadedListener(adUnitId, nativeAdsMap, context);
        AdLoader.Builder builder = new AdLoader.Builder(context, adUnitId);
        builder.forUnifiedNativeAd(unifiedNativeAdLoadedListener);
        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(muted)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT) // todo:: get from config
                .build();
        builder.withNativeAdOptions(adOptions);

        adLoader = builder.withAdListener(adListener).build();
    }

    public void loadAds(){
        adLoader.loadAds(adRequest, totalAds);
//        for (int i = 0; i<totalAds; i++){
//            adLoader.loadAd(adRequest);
//        }
    }

    public void loadAd(){
        adLoader.loadAd(adRequest);
        fillAd();
    }

    public void fillAd(){
        System.out.println("younes I am in filling ad " + nativeAdsMap.get(true).size() + " " + nativeAdsMap.get(false).size() + " " + totalAds);
        if (!isLoading()){
            for (int i = 0; i<(totalAds-nativeAdsMap.get(muted).size()); i++){
                System.out.println("younes I am in filling ad in for "+ i);
                adLoader.loadAd(adRequest);
            }
        }
    }

    public UnifiedNativeAd getAd(){
        long now = System.currentTimeMillis();
        Pair<Long, UnifiedNativeAd> ad;
        while (true){
            if (nativeAdsMap.get(muted).size() > 0){
                ad = nativeAdsMap.get(muted).pop();
//                fillAd();
                if ((ad.first - now) < expirationInterval){
                    System.out.println("younes I have the ad in given interval");
                    break;
                }
            }else{
                return null;
            }
        }
        fillAd();
        return ad.second;
    }

    public Boolean isLoading(){
        if (adLoader != null){
            return adLoader.isLoading();
        }
        return false;
    }

    public WritableMap hasLoadedAd(){
        WritableMap args = Arguments.createMap();
        args.putInt("muted", nativeAdsMap.get(true).size());
        args.putInt("unMuted", nativeAdsMap.get(false).size());
        return args;
    }
}
