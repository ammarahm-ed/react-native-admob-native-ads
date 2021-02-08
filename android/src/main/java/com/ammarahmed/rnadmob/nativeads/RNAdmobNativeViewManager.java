package com.ammarahmed.rnadmob.nativeads;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
 
import java.util.Map;

import javax.annotation.Nullable;


public class RNAdmobNativeViewManager extends ViewGroupManager<RNAdmobNativeView> {


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
    public static final String PROP_PAUSE_AD_RELOAD = "pauseAdReload";
    public static final String PROP_MEDIA_ASPECT_RATIO = "mediaAspectRatio";
    public static final String PROP_VIDEO_OPTIONS = "videoOptions";
    public static final String PROP_MEDIATION_OPTIONS = "mediationOptions";
    public static final String PROP_TARGETING_OPTIONS = "targetingOptions";

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
    protected RNAdmobNativeView createViewInstance(ThemedReactContext reactContext) {
       
        return new RNAdmobNativeView(reactContext);

    }

    @ReactProp(name = "messagingModuleName")
    public void setMessagingModuleName(RNAdmobNativeView nativeAdWrapper, String moduleName) {
        nativeAdWrapper.setMessagingModuleName(moduleName);
    }


    @Override
    public void addView(RNAdmobNativeView parent, View child, int index) {
        //super.addView(parent, child, index);
        parent.addNewView(child, index);

    }


    @ReactProp(name = PROP_TARGETING_OPTIONS)
    public void setPropTargetingOptions(final RNAdmobNativeView nativeAdWrapper, final ReadableMap options) {
        nativeAdWrapper.setTargetingOptions(options);
    }

    @ReactProp(name = PROP_VIDEO_OPTIONS)
    public void setVideoOptions(final RNAdmobNativeView nativeAdWrapper, final ReadableMap options) {
        nativeAdWrapper.setVideoOptions(options);
    }

    @ReactProp(name = PROP_MEDIATION_OPTIONS)
    public void setMediationOptions(final RNAdmobNativeView nativeAdWrapper, final ReadableMap options) {
        nativeAdWrapper.setMediationOptions(options);
    }

    @ReactProp(name = PROP_NON_PERSONALIZED_ADS, defaultBoolean = false)
    public void setPropNonPersonalizedAds(final RNAdmobNativeView nativeAdWrapper, final boolean npa) {

        nativeAdWrapper.setRequestNonPersonalizedAdsOnly(npa);
    }



    @ReactProp(name = PROP_AD_CHOICES_PLACEMENT)
    public void setPropAdChoicesPlacement(final RNAdmobNativeView nativeAdWrapper, final int location) {

        nativeAdWrapper.setAdChoicesPlacement(location);

    }




    @ReactProp(name = PROP_MEDIA_ASPECT_RATIO)
    public void setMediaAspectRatio(final RNAdmobNativeView nativeAdWrapper, final int type) {
        nativeAdWrapper.setMediaAspectRatio(type);
    }




    @ReactProp(name = PROP_MEDIA_VIEW)
    public void setMediaView(final RNAdmobNativeView nativeAdWrapper, final int id) {

        nativeAdWrapper.addMediaView(id);
        nativeAdWrapper.setNativeAd();
    }

    @ReactProp(name = PROP_HEADLINE_VIEW)
    public void setHeadlineView(final RNAdmobNativeView nativeAdWrapper, final int id) {

        TextView view = (TextView)nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setHeadlineView(view);
        nativeAdWrapper.setNativeAd();
    }

    @ReactProp(name = PROP_TAGLINE_VIEW)
    public void setPropTaglineView(final RNAdmobNativeView nativeAdWrapper, final int id) {

        TextView view = (TextView)nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setBodyView(view);
        nativeAdWrapper.setNativeAd();
    }

    @ReactProp(name = PROP_ADVERTISER_VIEW)
    public void setPropAdvertiserView(final RNAdmobNativeView nativeAdWrapper, final int id) {

        TextView view = (TextView)nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setAdvertiserView(view);
        nativeAdWrapper.setNativeAd();
    }

    @ReactProp(name = PROP_IMAGE_VIEW)
    public void setPropImageView(final RNAdmobNativeView nativeAdWrapper, final int id) {

        ImageView view = (ImageView) nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setImageView(view);
        nativeAdWrapper.setNativeAd();
    }

    @ReactProp(name = PROP_ICON_VIEW)
    public void setPropIconView(final RNAdmobNativeView nativeAdWrapper, final int id) {

        ImageView view = (ImageView) nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setIconView(view);
        nativeAdWrapper.setNativeAd();
    }

    @ReactProp(name = PROP_STORE_VIEW)
    public void setPropStoreView(final RNAdmobNativeView nativeAdWrapper, final int id) {

        TextView view = (TextView) nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setStoreView(view);
        nativeAdWrapper.setNativeAd();
    }

    @ReactProp(name = PROP_PRICE_VIEW)
    public void setPropPriceView(final RNAdmobNativeView nativeAdWrapper, final int id) {

        TextView view = (TextView) nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setPriceView(view);
        nativeAdWrapper.setNativeAd();
    }

    @ReactProp(name = PROP_STAR_RATING_VIEW)
    public void setPropStarRatingView(final RNAdmobNativeView nativeAdWrapper, final int id) {

        View view = nativeAdWrapper.findViewById(id);
        nativeAdWrapper.nativeAdView.setStarRatingView(view);
        nativeAdWrapper.setNativeAd();

    }

    @ReactProp(name = PROP_CALL_TO_ACTION_VIEW)
    public void setPropCallToActionView(final RNAdmobNativeView nativeAdWrapper, final int id) {

            View view = nativeAdWrapper.findViewById(id);
            nativeAdWrapper.nativeAdView.setCallToActionView(view);
            nativeAdWrapper.setNativeAd();


    }

    public static final int COMMAND_LOAD_AD = 1;

    @Override
    public @Nullable
    Map<String, Integer> getCommandsMap() {
        return MapBuilder.<String, Integer>builder()
                .put("loadAd", COMMAND_LOAD_AD)
                .build();
    }


    @Override
    public void receiveCommand(RNAdmobNativeView nativeAdWrapper, int commandId, @Nullable ReadableArray args) {
        switch (commandId) {
            case COMMAND_LOAD_AD:
                nativeAdWrapper.loadAd();
                break;
        }
    }


    @ReactProp(name = PROP_AD_UNIT_ID)
    public void setPropAdUnitId(final RNAdmobNativeView nativeAdWrapper, final String adUnitId) {
        if (adUnitId == null) return;
        nativeAdWrapper.setAdUnitId(adUnitId);

    }

    @Override
    public void onDropViewInstance(@NonNull RNAdmobNativeView nativeAdWrapper) {
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
