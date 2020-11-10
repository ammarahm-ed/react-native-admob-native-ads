package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.Map;

public class onUnifiedNativeAdLoadedListener implements UnifiedNativeAd.OnUnifiedNativeAdLoadedListener {
    String adUnitId;
    Map<String, ArrayList<UnifiedNativeAd>> nativeAds;
    Context mContext;

    public onUnifiedNativeAdLoadedListener(String adUnitId, Map<String, ArrayList<UnifiedNativeAd>> nativeAds, Context context) {
        this.adUnitId = adUnitId;
        this.nativeAds = nativeAds;
        this.mContext = context;
    }

    @Override
    public void onUnifiedNativeAdLoaded(UnifiedNativeAd nativeAd) {
        ArrayList<UnifiedNativeAd> temp;
        if (this.nativeAds.containsKey(this.adUnitId)) {
            temp = this.nativeAds.get(this.adUnitId);
        } else {
            temp = new ArrayList<>();
        }
        temp.add(nativeAd);
        WritableMap args = Arguments.createMap();
        args.putInt(this.adUnitId, temp.size());
        EventEmitter.sendEvent((ReactContext) this.mContext, Constants.EVENT_AD_PRELOAD_LOADED, args);
        this.nativeAds.put(this.adUnitId, temp);
    }
}