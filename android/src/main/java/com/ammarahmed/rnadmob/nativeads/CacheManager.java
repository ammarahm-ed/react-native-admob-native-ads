package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.os.Bundle;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class CacheManager {

    Map<String, RNAdMobUnifiedAdWrapper> nativeAdsMap = new HashMap<>();

    public boolean isLoading(String id) {
        if (nativeAdsMap.get(id) != null) {
            return nativeAdsMap.get(id).isLoading();
        } else {
            return false;
        }
    }

    public int numberOfAds(String id) {
        if (nativeAdsMap.containsKey(id)) {
            return nativeAdsMap.get(id).nativeAdsMap.get(true).size();
        } else {
            return 0;
        }

    }

    public void attachAdListener(String id, AdListener listener) {
        if (nativeAdsMap.get(id) != null){
            nativeAdsMap.get(id).attachAdListener(listener);
        }
    }

    public void detachAdListener(String id) {
        if (nativeAdsMap.get(id) != null){
            nativeAdsMap.get(id).detachAdListener();
        }
    }

    public String registerAd(Context context, ReadableMap config) {
        try {
            String adUnitID;
            if (config.hasKey("adUnitId") && config.getString("adUnitId") != null){
                adUnitID = config.getString("adUnitId");
                nativeAdsMap.put(adUnitID, new RNAdMobUnifiedAdWrapper(context, config));
                return adUnitID;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void unRegisterAd(String id) {
        nativeAdsMap.remove(id);
    }

    public void resetCache(){
        nativeAdsMap.clear();
    }

    public void requestAds(String adUnitId){
        nativeAdsMap.get(adUnitId).loadAds();
    }

    public void requestAd(String adUnitId){
        nativeAdsMap.get(adUnitId).loadAd();
    }

    public Boolean isRegistered(String adUnitID){
        return nativeAdsMap.containsKey(adUnitID);
    }

    public UnifiedNativeAd getNativeAd(String id) {

        if (nativeAdsMap.containsKey(id)) {
            return nativeAdsMap.get(id).getAd();
        } else {
            return null;
        }
    }

    public WritableMap hasLoadedAd(String id) {

        if (nativeAdsMap.containsKey(id)) {
            return nativeAdsMap.get(id).hasLoadedAd();
        } else {
            WritableMap args = Arguments.createMap();
            args.putInt("muted", 0);
            args.putInt("unMuted", 0);
            return args;
        }
    }

}

