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


    public void registerAd(Context context, ReadableMap config) {

        try {
            String adUnitID;
            if (config.hasKey("adUnitId") && config.getString("adUnitId") != null){
                adUnitID = config.getString("adUnitId");
                System.out.println("younes start loading for "+adUnitID);
                nativeAdsMap.put(adUnitID, new RNAdMobUnifiedAdWrapper(context, config));
            }
        } catch (Exception e) {
            System.out.println("younes error in "+ e.getMessage());
            e.printStackTrace();
        }
    }


    public UnifiedNativeAd getNativeAd(String id) {

        if (nativeAdsMap.containsKey(id)) {
            return nativeAdsMap.get(id).getAd();
        } else {
            return null;
        }
    }

//    public WritableMap hasLoadedAd(String id) {
//
//        if (nativeAdsMap.containsKey(id) && nativeAdsMap.get(id).size() != 0) {
//            WritableMap args = Arguments.createMap();
//            args.putBoolean(id, true);
//            return args;
//        } else {
//            WritableMap args = Arguments.createMap();
//            args.putBoolean(id, false);
//            return args;
//        }
//    }

}

