package com.ammarahmed.rnadmob.nativeads;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableNativeArray;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class RNAdMobNativeViewManager extends ViewGroupManager<RNNativeAdWrapper> {
    private static final String TAG = "RNAdMobNativeViewManag";
    public static final String REACT_CLASS = "RNGADNativeView";
    protected UnifiedNativeAd unifiedNativeAd;
    private RNNativeAdWrapper nativeAdView;
    private ThemedReactContext mContext;
    private String admobAdUnitId = "";

    public static final String EVENT_AD_FAILED_TO_LOAD = "onAdFailedToLoad";
    public static final String EVENT_AD_CLICKED = "onAdClicked";
    public static final String EVENT_AD_CLOSED = "onAdClosed";
    public static final String EVENT_AD_OPENED = "onAdOpened";
    public static final String EVENT_AD_IMPRESSION = "onAdImpression";
    public static final String EVENT_AD_LOADED = "onAdLoaded";
    public static final String EVENT_AD_LEFT_APPLICATION = "onAdLeftApplication";
    public static final String EVENT_UNIFIED_NATIVE_AD_LOADED = "onUnifiedNativeAdLoaded";

    public String adHeadline = "adHeadlineView";
    public String adTagline = "adTaglineView";
    public String adAdvertiser = "adAdvertiserView";
    public String adStarRating = "adStarRating";
    public String adImageView = "adImageView";
    public String adIconView = "adIconView";
    public String adCallToAction = "adCallToAction";
    public String adStoreView = "adStoreView";
    public int AdChoicesViewId = 733;



    public static final String PROP_TEST_DEVICES = "testDevices";
    public static final String PROP_AD_UNIT_ID = "adUnitID";


    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNNativeAdWrapper createViewInstance(ThemedReactContext reactContext) {
        mContext = reactContext;
        nativeAdView = new RNNativeAdWrapper(reactContext);

        return nativeAdView;
    }

    @Override
    public void addView(RNNativeAdWrapper parent, View child, int index) {
        //super.addView(parent, child, index);

        nativeAdView.addNewView(child,index);


    }

    /*


    @ReactProp(name = "adChoicesView")
    public void setAdChoicesView(final RNNativeAdWrapper view, final int adChoicesViewId) {

        AdChoicesView adChoicesView = nativeAdView.findViewById(adChoicesViewId);
        if (adChoicesView != null)
           // nativeAdView.setAdChoicesView(adChoicesView);


    }

     */

    @ReactProp(name = "adMediaView")
    public void setMediaView(final RNNativeAdWrapper view, final int mediaViewId) {



       nativeAdView.addMediaView(mediaViewId);


    }

    @ReactProp(name = PROP_TEST_DEVICES)
    public void setPropTestDevices(final RNNativeAdWrapper view, final ReadableArray testDevices) {
        ReadableNativeArray nativeArray = (ReadableNativeArray) testDevices;
        ArrayList<Object> list = nativeArray.toArrayList();

        List<String> testDeviceIds = Arrays.asList(list.toArray(new String[list.size()]));
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
    }

    @javax.annotation.Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
        String[] events = new String[]{
                EVENT_AD_FAILED_TO_LOAD,
                EVENT_AD_CLICKED,
                EVENT_AD_CLOSED,
                EVENT_AD_OPENED,
                EVENT_AD_IMPRESSION,
                EVENT_AD_LOADED,
                EVENT_AD_LEFT_APPLICATION,
                EVENT_UNIFIED_NATIVE_AD_LOADED
        };
        for (String event : events) {
            builder.put(event, MapBuilder.of("registrationName", event));
        }
        return builder.build();
    }

    @ReactProp(name = PROP_AD_UNIT_ID)
    public void setPropAdUnitId(final RNNativeAdWrapper view, final String adUnitId) {

        admobAdUnitId = adUnitId;
        nativeAdView.setAdUnitId(adUnitId);

    }
}
