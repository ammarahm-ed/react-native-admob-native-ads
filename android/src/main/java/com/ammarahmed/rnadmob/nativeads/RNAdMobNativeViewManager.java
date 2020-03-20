package com.ammarahmed.rnadmob.nativeads;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.ArrayList;
import java.util.Map;


public class RNAdMobNativeViewManager extends ViewGroupManager<ReactNativeView> {

    public static final String REACT_CLASS = "RNGADNativeView";
    public static final String PROP_AD_UNIT_ID = "adUnitID";
    public static final String EVENT_AD_FAILED_TO_LOAD = "onAdFailedToLoad";
    public static final String EVENT_AD_CLICKED = "onAdClicked";
    public static final String EVENT_AD_CLOSED = "onAdClosed";
    public static final String EVENT_AD_OPENED = "onAdOpened";
    public static final String EVENT_AD_IMPRESSION = "onAdImpression";
    public static final String EVENT_AD_LOADED = "onAdLoaded";
    public static final String EVENT_AD_LEFT_APPLICATION = "onAdLeftApplication";
    public static final String EVENT_UNIFIED_NATIVE_AD_LOADED = "onUnifiedNativeAdLoaded";
    public static final String PROP_ADSIZE = "adSize";
    public static final String PROP_HEADLINE_COLOR = "headlineTextColor";
    public static final String PROP_TAGLINE_COLOR = "descriptionTextColor";
    public static final String PROP_ADVERTISER_COLOR = "advertiserTextColor";
    public static final String PROP_RATING_COLOR = "ratingBarColor";
    public static final String PROP_BUTTON_STYLE = "buttonStyle";
    public static final String PROP_BACKGROUND_STYLE = "backgroundStyle";
    public static final String PROP_BACKGROUND_COLOR = "background";
    public static final String PROP_TEST_DEVICES = "testDevices";
    public static final int COMMAND_LOAD_NATIVE = 1;


    private static final String TAG = "RNAdMobNativeViewManager";
    private static final String COLOR_REGEX = "^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{8})$";

    private ThemedReactContext mContext;
    private ReactNativeView nativeAdView;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected ReactNativeView createViewInstance(ThemedReactContext reactContext) {
        mContext = reactContext;
        nativeAdView = new ReactNativeView(reactContext);

        return nativeAdView;
    }

    @ReactProp(name = PROP_TEST_DEVICES)
    public void setPropTestDevices(final ReactNativeView view, final ReadableArray testDevices) {
        ReadableNativeArray nativeArray = (ReadableNativeArray) testDevices;
        ArrayList<Object> list = nativeArray.toArrayList();
        view.setTestDevices(list.toArray(new String[list.size()]));
    }

    @Override
    public void addView(ReactNativeView parent, View child, int index) {

        throw new RuntimeException("CYNativeAdView cannot have subviews");
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


    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of("loadNativeAd", COMMAND_LOAD_NATIVE);
    }

    @Override
    public void receiveCommand(ReactNativeView root, int commandId, @javax.annotation.Nullable ReadableArray args) {
        switch (commandId) {
            case COMMAND_LOAD_NATIVE:
                root.loadNativeAd(args.getString(0));
                break;
        }
    }


    @ReactProp(name = PROP_ADSIZE)
    public void setAdSize(final ReactNativeView view, final String template) {

        view.setAdSize(template);
    }

    @ReactProp(name = PROP_BUTTON_STYLE)
    public void setButtonStyle(final ReactNativeView view, final ReadableMap style) {
        if (style == null) return;
        String borderColor = style.getString("borderColor");
        int borderRadius = style.getInt("borderRadius");
        int borderWidth = style.getInt("borderWidth");
        String textColor = style.getString("textColor");
        String backgroundColor = style.getString("backgroundColor");
        view.setButtonStyle(textColor, backgroundColor, borderColor, borderWidth, borderRadius);

    }

    @ReactProp(name = PROP_BACKGROUND_STYLE)
    public void setBackgroundStyle(final ReactNativeView view, final ReadableMap style) {
        if (style == null) return;
        String borderColor = style.getString("borderColor");
        int borderRadius = style.getInt("borderRadius");
        int borderWidth = style.getInt("borderWidth");
        String backgroundColor = style.getString("backgroundColor");

        view.setBackgroundStyle(backgroundColor, borderColor, borderWidth, borderRadius);

    }


    @ReactProp(name = PROP_HEADLINE_COLOR)
    public void setHeadlineColor(final ReactNativeView view, final String color) {
        if (color == null) return;
        view.setHeadlineColor(Color.parseColor(color));

    }

    @ReactProp(name = PROP_ADVERTISER_COLOR)
    public void setAdvertiserColor(final ReactNativeView view, final String color) {
        if (color == null) return;
        view.setAdvertiserTextColor(Color.parseColor(color));

    }

    @ReactProp(name = PROP_TAGLINE_COLOR)
    public void setTaglineColor(final ReactNativeView view, final String color) {
        if (color == null) return;
        view.setBodyTextColor(Color.parseColor(color));

    }

    @ReactProp(name = PROP_RATING_COLOR)
    public void setRatingColor(final ReactNativeView view, final String color) {
        if (color == null) return;
        view.setRatingColor(Color.parseColor(color));

    }


    @ReactProp(name = PROP_AD_UNIT_ID)
    public void setPropAdUnitId(final ReactNativeView view, final String adUnitId) {

        view.setAdmobAdUnitId(adUnitId);
    }

    @ReactProp(name = PROP_BACKGROUND_COLOR)
    public void setBackgroundColor(final ReactNativeView view, final String backgroundColor) {

        if (backgroundColor == null) return;
        view.setBgColor(Color.parseColor(backgroundColor));

    }


    private int getColor(@NonNull String colorString) throws JSApplicationIllegalArgumentException {
        if (colorString.matches(COLOR_REGEX)) {
            return Color.parseColor(colorString);
        } else {
            throw new JSApplicationIllegalArgumentException("Invalid arrowColor property: " + colorString);
        }
    }


}
