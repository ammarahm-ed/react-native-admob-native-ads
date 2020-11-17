package com.ammarahmed.rnadmob.nativeads;

import android.view.View;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class RNAdMobNativeViewManager extends ViewGroupManager<RNNativeAdWrapper> {


    public static final String REACT_CLASS = "RNGADNativeView";

    public static final String EVENT_AD_FAILED_TO_LOAD = "onAdFailedToLoad";
    public static final String EVENT_AD_CLICKED = "onAdClicked";
    public static final String EVENT_AD_CLOSED = "onAdClosed";
    public static final String EVENT_AD_OPENED = "onAdOpened";
    public static final String EVENT_AD_IMPRESSION = "onAdImpression";
    public static final String EVENT_AD_LOADED = "onAdLoaded";
    public static final String EVENT_AD_LEFT_APPLICATION = "onAdLeftApplication";
    public static final String EVENT_UNIFIED_NATIVE_AD_LOADED = "onUnifiedNativeAdLoaded";
    public static final String PROP_DELAY_AD_LOAD = "delayAdLoad";
    public static final String PROP_TEST_DEVICES = "testDevices";
    public static final String PROP_AD_UNIT_ID = "adUnitID";
    public static final String PROP_MEDIA_VIEW = "mediaview";
    public static final String PROP_REFRESH_INTERVAL = "refreshInterval";
    public static final String PROP_HEADLINE_VIEW = "headline";
    public static final String PROP_TAGLINE_VIEW = "tagline";
    public static final String PROP_ADVERTISER_VIEW = "advertiser";
    public static final String PROP_STORE_VIEW = "store";
    public static final String PROP_IMAGE_VIEW = "image";
    public static final String PROP_CALL_TO_ACTION_VIEW = "callToAction";
    public static final String PROP_PRICE_VIEW = "price";
    public static final String PROP_ICON_VIEW = "icon";
    public static final String PROP_STAR_RATING_VIEW = "starrating";
    public static final String PROP_AD_CHOICES_PLACEMENT = "adChoicesPlacement";
    public static final String PROP_NON_PERSONALIZED_ADS = "requestNonPersonalizedAdsOnly";


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
                EVENT_UNIFIED_NATIVE_AD_LOADED,
        };
        for (String event : events) {
            builder.put(event, MapBuilder.of("registrationName", event));
        }
        return builder.build();
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNNativeAdWrapper createViewInstance(ThemedReactContext reactContext) {
       
        return new RNNativeAdWrapper(reactContext);

    }

    @ReactProp(name = "messagingModuleName")
    public void setMessagingModuleName(RNNativeAdWrapper nativeAdWrapper, String moduleName) {
        nativeAdWrapper.setMessagingModuleName(moduleName);
    }


    @Override
    public void addView(RNNativeAdWrapper parent, View child, int index) {
        //super.addView(parent, child, index);
        parent.addNewView(child, index);

    }

    @ReactProp(name = PROP_REFRESH_INTERVAL)
    public void setRefreshInterval(final RNNativeAdWrapper nativeAdWrapper, final int interval) {

        nativeAdWrapper.setAdRefreshInterval(interval);

    }

    @ReactProp(name = PROP_NON_PERSONALIZED_ADS, defaultBoolean = false)
    public void setPropNonPersonalizedAds(final RNNativeAdWrapper nativeAdWrapper, final boolean npa) {

        nativeAdWrapper.setRequestNonPersonalizedAdsOnly(npa);
    }


    @ReactProp(name = PROP_AD_CHOICES_PLACEMENT)
    public void setPropAdChoicesPlacement(final RNNativeAdWrapper nativeAdWrapper, final int location) {

        nativeAdWrapper.setAdChoicesPlacement(location);
    }


    @ReactProp(name = PROP_DELAY_AD_LOAD)
    public void setPropDelayAdLoad(final RNNativeAdWrapper nativeAdWrapper, final int delay) {

        nativeAdWrapper.setLoadWithDelay(delay);

    }

    @ReactProp(name = PROP_MEDIA_VIEW)
    public void setMediaView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

        nativeAdWrapper.addMediaView(id);

    }

    @ReactProp(name = PROP_HEADLINE_VIEW)
    public void setHeadlineView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setHeadlineView(view);



    }

    @ReactProp(name = PROP_TAGLINE_VIEW)
    public void setPropTaglineView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
            nativeAdWrapper.nativeAdView.setBodyView(view);

    }

    @ReactProp(name = PROP_ADVERTISER_VIEW)
    public void setPropAdvertiserView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
            nativeAdWrapper.nativeAdView.setAdvertiserView(view);

    }

    @ReactProp(name = PROP_IMAGE_VIEW)
    public void setPropImageView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
            nativeAdWrapper.nativeAdView.setImageView(view);

    }

    @ReactProp(name = PROP_ICON_VIEW)
    public void setPropIconView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setIconView(view);

    }

    @ReactProp(name = PROP_STORE_VIEW)
    public void setPropStoreView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
            nativeAdWrapper.nativeAdView.setStoreView(view);

    }

    @ReactProp(name = PROP_PRICE_VIEW)
    public void setPropPriceView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
            nativeAdWrapper.nativeAdView.setPriceView(view);

    }

    @ReactProp(name = PROP_STAR_RATING_VIEW)
    public void setPropStarRatingView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setStarRatingView(view);

    }

    @ReactProp(name = PROP_CALL_TO_ACTION_VIEW)
    public void setPropCallToActionView(final RNNativeAdWrapper nativeAdWrapper, final int id) {

            View view = nativeAdWrapper.findViewById(id);
            nativeAdWrapper.nativeAdView.setCallToActionView(view);

    }


    @ReactProp(name = PROP_TEST_DEVICES)
    public void setPropTestDevices(final RNNativeAdWrapper nativeAdWrapper, final ReadableArray testDevices) {
      //  ReadableNativeArray nativeArray = (ReadableNativeArray) testDevices;
      //  ArrayList<Object> list = nativeArray.toArrayList();

      //  List<String> testDeviceIds = Arrays.asList(list.toArray(new String[list.size()]));
      //  RequestConfiguration configuration =
      //          new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
     //   MobileAds.setRequestConfiguration(configuration);
    }


    @ReactProp(name = PROP_AD_UNIT_ID)
    public void setPropAdUnitId(final RNNativeAdWrapper nativeAdWrapper, final String adUnitId) {
        if (adUnitId == null) return;
        nativeAdWrapper.setAdUnitId(adUnitId);

    }

    @Override
    public void onDropViewInstance(@NonNull RNNativeAdWrapper nativeAdWrapper) {
        super.onDropViewInstance(nativeAdWrapper);
        nativeAdWrapper.removeHandler();
        if (nativeAdWrapper.unifiedNativeAd != null){
            nativeAdWrapper.unifiedNativeAd.destroy();
        }
	if (nativeAdWrapper.nativeAdView != null){
            nativeAdWrapper.nativeAdView.destroy();
        }
    }


}
