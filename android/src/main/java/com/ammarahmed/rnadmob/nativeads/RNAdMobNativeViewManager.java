package com.ammarahmed.rnadmob.nativeads;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;


public class RNAdMobNativeViewManager extends ViewGroupManager<RNAdMobNativeView> {


    public static final String REACT_CLASS = "RNGADNativeView";

    public static final String EVENT_AD_FAILED_TO_LOAD = "onAdFailedToLoad";
    public static final String EVENT_AD_CLICKED = "onAdClicked";
    public static final String EVENT_AD_CLOSED = "onAdClosed";
    public static final String EVENT_AD_OPENED = "onAdOpened";
    public static final String EVENT_AD_IMPRESSION = "onAdImpression";
    public static final String EVENT_AD_LOADED = "onAdLoaded";
    public static final String EVENT_AD_LEFT_APPLICATION = "onAdLeftApplication";
    public static final String EVENT_UNIFIED_NATIVE_AD_LOADED = "onUnifiedNativeAdLoaded";
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
    public static final String PROP_AD_TYPE = "type";

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
    protected RNAdMobNativeView createViewInstance(ThemedReactContext reactContext) {

        return new RNAdMobNativeView(reactContext);

    }

    @ReactProp(name = "messagingModuleName")
    public void setMessagingModuleName(RNAdMobNativeView nativeAdWrapper, String moduleName) {
        nativeAdWrapper.setMessagingModuleName(moduleName);
    }


    @Override
    public void addView(RNAdMobNativeView parent, View child, int index) {
        //super.addView(parent, child, index);
        parent.addNewView(child, index);

    }

    @ReactProp(name = PROP_REFRESH_INTERVAL)
    public void setRefreshInterval(final RNAdMobNativeView nativeAdWrapper, final int interval) {

        nativeAdWrapper.setAdRefreshInterval(interval);

    }

    @ReactProp(name = PROP_AD_TYPE)
    public void setPropAdType(final RNAdMobNativeView nativeAdWrapper, final String type) {

        nativeAdWrapper.setAdType(type);

    }


    @ReactProp(name = PROP_MEDIA_VIEW)
    public void setMediaView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        nativeAdWrapper.addMediaView(id);

    }

    @ReactProp(name = PROP_HEADLINE_VIEW)
    public void setHeadlineView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setHeadlineView(view);


    }

    @ReactProp(name = PROP_TAGLINE_VIEW)
    public void setPropTaglineView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setBodyView(view);

    }

    @ReactProp(name = PROP_ADVERTISER_VIEW)
    public void setPropAdvertiserView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setAdvertiserView(view);

    }

    @ReactProp(name = PROP_IMAGE_VIEW)
    public void setPropImageView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setImageView(view);

    }

    @ReactProp(name = PROP_ICON_VIEW)
    public void setPropIconView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setIconView(view);

    }

    @ReactProp(name = PROP_STORE_VIEW)
    public void setPropStoreView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setStoreView(view);

    }

    @ReactProp(name = PROP_PRICE_VIEW)
    public void setPropPriceView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setPriceView(view);

    }

    @ReactProp(name = PROP_STAR_RATING_VIEW)
    public void setPropStarRatingView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setStarRatingView(view);

    }

    @ReactProp(name = PROP_CALL_TO_ACTION_VIEW)
    public void setPropCallToActionView(final RNAdMobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setCallToActionView(view);

    }


    //@ReactProp(name = PROP_TEST_DEVICES)
    //public void setPropTestDevices(final RNAdMobNativeView nativeAdWrapper, final ReadableArray testDevices) {
    //  ReadableNativeArray nativeArray = (ReadableNativeArray) testDevices;
    //  ArrayList<Object> list = nativeArray.toArrayList();

    //  List<String> testDeviceIds = Arrays.asList(list.toArray(new String[list.size()]));
    //  RequestConfiguration configuration =
    //          new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
    //   MobileAds.setRequestConfiguration(configuration);
    // }

    @Override
    public void onDropViewInstance(@NonNull RNAdMobNativeView nativeAdWrapper) {
        super.onDropViewInstance(nativeAdWrapper);
        nativeAdWrapper.removeHandler();

        // Never destroy the preloaded ads because they are to be reused.
        if (nativeAdWrapper.unifiedNativeAd != null && !nativeAdWrapper.usingPreloadedAd) {
            nativeAdWrapper.unifiedNativeAd.destroy();
        }

        if (nativeAdWrapper.nativeAdView != null) {
            nativeAdWrapper.nativeAdView.destroy();
        }
    }

    public static final int COMMAND_LOAD_NATIVE_AD = 1;
    public static final int COMMAND_LOAD_NATIVE_VIDEO_AD = 2;
    public static final int COMMAND_LOAD_PRELOADED_NATIVE_AD = 3;
    public static final int COMMAND_LOAD_PRELOADED_VIDEO_AD = 4;


    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        // You need to implement this method and return a map with the readable
        // name and constant for each of your commands. The name you specify
        // here is what you'll later use to access it in react-native.
        return MapBuilder.of(
                "loadAd",
                COMMAND_LOAD_NATIVE_AD,
                "loadPreloadedAd",
                COMMAND_LOAD_PRELOADED_NATIVE_AD
        );
    }

    @Override
    public void receiveCommand(final RNAdMobNativeView view, int commandId, @Nullable ReadableArray args) {
        // This will be called whenever a command is sent from react-native.
        switch (commandId) {
            case COMMAND_LOAD_NATIVE_AD:
                view.loadAd(RNAdMobGlobals.preloader.adUnitID);
                view.setPreloadingMode(false);
                break;
            case COMMAND_LOAD_NATIVE_VIDEO_AD:
                view.loadAd(RNAdMobGlobals.preloader.videoAdUnitID);
                view.setPreloadingMode(false);
                break;
            case COMMAND_LOAD_PRELOADED_NATIVE_AD:
                view.loadPreloadedAd();
                view.setPreloadingMode(true);
                break;
            case COMMAND_LOAD_PRELOADED_VIDEO_AD:
                view.loadPreloadedVideoAd();
                view.setPreloadingMode(true);
        }
    }


}
