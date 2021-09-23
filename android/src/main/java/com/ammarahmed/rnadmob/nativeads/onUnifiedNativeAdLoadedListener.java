package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener;

import java.lang.Long;
import java.util.List;
import java.util.PriorityQueue;

public class onUnifiedNativeAdLoadedListener implements OnNativeAdLoadedListener {
    String repo;
    List<RNAdMobUnifiedAdContainer> nativeAds;
    Context mContext;
    Integer totalAds;

    public onUnifiedNativeAdLoadedListener(String repo, List<RNAdMobUnifiedAdContainer> nativeAds, Integer tAds, Context context) {
        this.repo = repo;
        this.nativeAds = nativeAds;
        this.mContext = context;
        this.totalAds = tAds;
    }

    @Override
    public void onNativeAdLoaded(NativeAd nativeAd) {
        if (this.nativeAds.size() > totalAds){
            // remove oldest ad if it is full
            RNAdMobUnifiedAdContainer toBeRemoved = null;
            Long time = System.currentTimeMillis();
            for (RNAdMobUnifiedAdContainer ad: this.nativeAds){
                if (ad.loadTime < time && ad.references <=0){
                    time = ad.loadTime;
                    toBeRemoved = ad;
                }
            }
            if (toBeRemoved !=  null){
                toBeRemoved.unifiedNativeAd.destroy();
                this.nativeAds.remove(toBeRemoved);
            }
        }
        this.nativeAds.add(new RNAdMobUnifiedAdContainer(nativeAd, System.currentTimeMillis(), 0));
        WritableMap args = Arguments.createMap();
        args.putInt(this.repo, this.nativeAds.size());
        EventEmitter.sendEvent((ReactContext) this.mContext, CacheManager.EVENT_AD_PRELOAD_LOADED, args);
    }
}