package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.util.Pair;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.lang.Long;
import java.util.Map;
import java.util.Stack;

public class onUnifiedNativeAdLoadedListener implements UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {
    String adUnitId;
    Map<Boolean, Stack<Pair<Long, UnifiedNativeAd>>> nativeAds;
    Context mContext;
    Boolean mute = true;

    public onUnifiedNativeAdLoadedListener(String adUnitId, Map<Boolean, Stack<Pair<Long, UnifiedNativeAd>>> nativeAds, Context context) {
        this.adUnitId = adUnitId;
        this.nativeAds = nativeAds;
        this.mContext = context;
    }

    public void setMute(Boolean m){
        mute = m;
    }

    @Override
    public void onUnifiedNativeAdLoaded(UnifiedNativeAd nativeAd) {
        System.out.println("younes ad loaded for : " + adUnitId);
        Stack<Pair<Long, UnifiedNativeAd>> temp;
        Pair<Long, UnifiedNativeAd> tempAd = new Pair<>(System.currentTimeMillis(), nativeAd);
        if (this.nativeAds.containsKey(mute)) {
            temp = this.nativeAds.get(mute);
        } else {
            temp = new Stack<>();
        }
        temp.push(tempAd);
        WritableMap args = Arguments.createMap();
        args.putInt(this.adUnitId, temp.size());
        EventEmitter.sendEvent((ReactContext) this.mContext, Constants.EVENT_AD_PRELOAD_LOADED, args);
        this.nativeAds.put(mute, temp);
    }
}