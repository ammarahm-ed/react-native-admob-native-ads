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
    String repo;
    Stack<Pair<Long, UnifiedNativeAd>> nativeAds;
    Context mContext;

    public onUnifiedNativeAdLoadedListener(String repo, Stack<Pair<Long, UnifiedNativeAd>> nativeAds, Context context) {
        this.repo = repo;
        this.nativeAds = nativeAds;
        this.mContext = context;
    }

    @Override
    public void onUnifiedNativeAdLoaded(UnifiedNativeAd nativeAd) {
        this.nativeAds.push(new Pair<>(System.currentTimeMillis(), nativeAd));
        WritableMap args = Arguments.createMap();
        args.putInt(this.repo, this.nativeAds.size());
        EventEmitter.sendEvent((ReactContext) this.mContext, Constants.EVENT_AD_PRELOAD_LOADED, args);
    }
}