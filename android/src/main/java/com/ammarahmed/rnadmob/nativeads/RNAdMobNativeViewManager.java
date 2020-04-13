package com.ammarahmed.rnadmob.nativeads;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.UIManager;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.touch.JSResponderHandler;
import com.facebook.react.uimanager.ReactRootViewTagGenerator;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.StateWrapper;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class RNAdMobNativeViewManager extends ViewGroupManager<UnifiedNativeAdView> {

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
    public static final String PROP_TEST_DEVICES = "testDevices";
    protected UnifiedNativeAd unifiedNativeAd;
    private String admobAdUnitId = "ca-app-pub-3940256099942544/3986624511";

    private static final String TAG = "RNAdMobNativeViewManager";
    private static final String COLOR_REGEX = "^#([0-9A-Fa-f]{6}|[0-9A-Fa-f]{8})$";
    public static final int COMMAND_LOAD_NATIVE = 1;
    private ThemedReactContext mContext;
    private UnifiedNativeAdView nativeAdView;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected UnifiedNativeAdView createViewInstance(ThemedReactContext reactContext) {
        mContext = reactContext;
        nativeAdView = new UnifiedNativeAdView(reactContext);
        return nativeAdView;
    }

    @Override
    public void addView(UnifiedNativeAdView parent, View child, int index) {
        super.addView(parent, child, index);
    }

    private void loadAd() {

        AdLoader.Builder builder = new AdLoader.Builder(mContext, admobAdUnitId);
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd nativeAd) {
                if (nativeAd != null) {
                    unifiedNativeAd = nativeAd;
                    nativeAdView.setNativeAd(unifiedNativeAd);
                }

                WritableMap args = Arguments.createMap();
                args.putString("headlineText", nativeAd.getHeadline());
                args.putString("bodyText", nativeAd.getBody());
                args.putString("advertiserText", nativeAd.getAdvertiser());
                args.putString("headlineText", nativeAd.getHeadline());
                args.putString("callToAction", nativeAd.getCallToAction());

                if (nativeAd.getStore() != null) {
                    args.putString("storeName", nativeAd.getStore());
                }

                if (nativeAd.getStarRating() != null) {
                    args.putInt("starRating", nativeAd.getStarRating().intValue());
                }

                WritableArray images = Arguments.createArray();

                for (int i = 0; i < nativeAd.getImages().size(); i++) {
                    WritableMap map = Arguments.createMap();

                    map.putString("uri", nativeAd.getImages().get(i).getUri().toString());
                    map.putInt("height", nativeAd.getImages().get(i).getHeight());
                    map.putInt("width", nativeAd.getImages().get(i).getWidth());
                    map.putInt("width", nativeAd.getImages().get(i).getWidth());
                    images.pushMap(map);
                }
                args.putArray("images", images);
                WritableMap map = Arguments.createMap();
                map.putString("uri", nativeAd.getIcon().getUri().toString());
                map.putInt("height", nativeAd.getIcon().getHeight());
                map.putInt("width", nativeAd.getIcon().getWidth());
                map.putInt("width", nativeAd.getIcon().getWidth());
                args.putMap("icon", map);
                sendEvent(RNAdMobNativeViewManager.EVENT_UNIFIED_NATIVE_AD_LOADED, args);


            }

        });


        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .setAdChoicesPlacement(NativeAdOptions.ADCHOICES_TOP_RIGHT)
                .build();
        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

                String errorMessage = "Unknown error";
                switch (i) {
                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        errorMessage = "Internal error, an invalid response was received from the ad server.";
                        break;
                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        errorMessage = "Invalid ad request, possibly an incorrect ad unit ID was given.";
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        errorMessage = "The ad request was unsuccessful due to network connectivity.";
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        errorMessage = "The ad request was successful, but no ad was returned due to lack of ad inventory.";
                        break;
                }
                WritableMap event = Arguments.createMap();
                WritableMap error = Arguments.createMap();
                error.putString("message", errorMessage);
                event.putMap("error", error);

                sendEvent(RNAdMobNativeViewManager.EVENT_AD_FAILED_TO_LOAD, event);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                sendEvent(RNAdMobNativeViewManager.EVENT_AD_CLOSED, null);
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                sendEvent(RNAdMobNativeViewManager.EVENT_AD_OPENED, null);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                sendEvent(RNAdMobNativeViewManager.EVENT_AD_CLICKED, null);

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                sendEvent(RNAdMobNativeViewManager.EVENT_AD_LOADED, null);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                sendEvent(RNAdMobNativeViewManager.EVENT_AD_IMPRESSION, null);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                sendEvent(RNAdMobNativeViewManager.EVENT_AD_LEFT_APPLICATION, null);
            }
        })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }


    @ReactProp(name = PROP_TEST_DEVICES)
    public void setPropTestDevices(final UnifiedNativeAdView view, final ReadableArray testDevices) {
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


    //com.facebook.react.uimanager.util.ReactFindViewUtil.findView();

    @ReactProp(name = PROP_AD_UNIT_ID)
    public void setPropAdUnitId(final UnifiedNativeAdView view, final String adUnitId) {
        admobAdUnitId = adUnitId;
        loadAd();
    }


    private void sendEvent(String name, @Nullable WritableMap event) {

        ReactContext reactContext = (ReactContext) mContext;
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                nativeAdView.getId(),
                name,
                event);
    }
}
