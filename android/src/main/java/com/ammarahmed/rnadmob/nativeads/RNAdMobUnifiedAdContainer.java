package com.ammarahmed.rnadmob.nativeads;

import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.Comparator;

import javax.annotation.Nullable;

public class RNAdMobUnifiedAdContainer {
    public Long loadTime;
    public Integer showCount;
    UnifiedNativeAd unifiedNativeAd;

    RNAdMobUnifiedAdContainer(UnifiedNativeAd nativeAd, Long t, @Nullable Integer n){
        unifiedNativeAd = nativeAd;
        showCount = n != null ? n : 0;
        loadTime = t;
    }
}

class RNAdMobUnifiedAdComparator implements Comparator<RNAdMobUnifiedAdContainer> {

    // Overriding compare() method of Comparator
    // for ascending order of showCount
    public int compare(RNAdMobUnifiedAdContainer ad1, RNAdMobUnifiedAdContainer ad2) {
        if (ad1.showCount > ad2.showCount)
            return 1;
        else if (ad1.showCount < ad2.showCount)
            return -1;
        return 0;
    }
}
