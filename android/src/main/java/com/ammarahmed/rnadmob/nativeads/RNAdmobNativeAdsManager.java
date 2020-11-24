package com.ammarahmed.rnadmob.nativeads;


import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.List;

public class RNAdmobNativeAdsManager extends ReactContextBaseJavaModule {

    private ReactApplicationContext reactContext;

    public RNAdmobNativeAdsManager(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        RNAdMobGlobals.preloader.attachAdListener(adListener);
    }

    @NonNull
    @Override
    public String getName() {
        return "RNAdmobNativeAdsManager";
    }


    @ReactMethod
    public void setRequestConfiguration(ReadableMap config) {
        if (config != null) {
            RequestConfiguration.Builder configuration = new RequestConfiguration.Builder();

            if (config.hasKey("maxAdContentRating")) {
                if (config.getString("maxAdContentRating") != null) {
                    String maxAdContentRating = config.getString("maxAdContentRating");
                    if (maxAdContentRating != null) {
                        if (maxAdContentRating.equals("UNSPECIFIED"))
                            maxAdContentRating = "";
                        configuration.setMaxAdContentRating(maxAdContentRating);
                    }
                }
            }

            if (config.hasKey("tagForChildDirectedTreatment")) {
                boolean tagForChildDirectedTreatment = config.getBoolean("tagForChildDirectedTreatment");
                configuration.setTagForChildDirectedTreatment(tagForChildDirectedTreatment ? 1 : 0);
            }
            if (config.hasKey("tagForUnderAgeOfConsent")) {
                boolean tagForUnderAgeOfConsent = config.getBoolean("tagForUnderAgeOfConsent");
                configuration.setTagForUnderAgeOfConsent(tagForUnderAgeOfConsent ? 1 : 0);
            }
            if (config.hasKey("testDeviceIds")) {
                ReadableNativeArray nativeArray = (ReadableNativeArray) config.getArray("testDeviceIds");
                if (nativeArray != null) {
                    ArrayList<Object> list = nativeArray.toArrayList();
                    List<String> testDeviceIds = new ArrayList<>(list.size());
                    for (Object object : list) {
                        testDeviceIds.add(object != null ? object.toString() : null);
                    }
                    configuration.setTestDeviceIds(testDeviceIds);
                }
            }

            MobileAds.setRequestConfiguration(configuration.build());
        }
        MobileAds.initialize(reactContext);
    }


    @ReactMethod
    public void isTestDevice(Promise promise) {
        AdRequest builder = new AdRequest.Builder().build();
        promise.resolve(builder.isTestDevice(getReactApplicationContext()));
    }


    @ReactMethod
    public void setNumberOfAdsToLoad(int numOfAdsToLoad, int numOfVideoAdsToLoad) {
        RNAdMobGlobals.preloader.setNumberOfAdsToLoad(numOfAdsToLoad, numOfVideoAdsToLoad);
    }

    @ReactMethod
    public void setAdUnitIds(String id, String vid) {
        RNAdMobGlobals.preloader.setAdUnitIDs(id, vid);
    }

    @ReactMethod
    public void setRequestNonPersonalizedAdsOnly(boolean npa) {
        RNAdMobGlobals.preloader.setRequestNonPersonalizedAdsOnly(npa);
    }

    @ReactMethod
    public void setMediaAspectRatio(int i) {
        RNAdMobGlobals.preloader.setMediaAspectRatio(i);
    }

    @ReactMethod
    public void setAdChoicesPlacement(int i) {
        RNAdMobGlobals.preloader.setAdChoicesPlacement(i);
    }

    @ReactMethod
    public void setVideosStartMuted(boolean muted) {
        RNAdMobGlobals.preloader.setVideosStartMuted(muted);
    }

    @ReactMethod
    public void preloadNativeAds() {
        RNAdMobGlobals.preloader.loadNativeAds(reactContext);

    }

    @ReactMethod
    public void preloadNativeVideoAds() {
        RNAdMobGlobals.preloader.loadNativeVideoAds(reactContext);

    }


    AdListener adListener = new AdListener() {
        @Override
        public void onAdFailedToLoad(LoadAdError loadAdError) {
            super.onAdFailedToLoad(loadAdError);

            WritableMap event = Arguments.createMap();
            WritableMap error = Arguments.createMap();
            if (loadAdError.getMessage() != null) {
                error.putString("message", loadAdError.getMessage());
            } else {
                error.putString("message", "unknown error occurred.");
            }
            event.putMap("error", error);
            sendEvent(RNAdMobNativeViewManager.EVENT_AD_FAILED_TO_LOAD, event);

        }

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
            sendEvent(RNAdMobGlobals.EVENT_AD_CLOSED, null);
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
            sendEvent(RNAdMobGlobals.EVENT_AD_OPENED, null);
        }

        @Override
        public void onAdClicked() {
            super.onAdClicked();
            sendEvent(RNAdMobGlobals.EVENT_AD_CLICKED, null);

        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            sendEvent(RNAdMobGlobals.EVENT_AD_LOADED, null);

        }

        @Override
        public void onAdImpression() {
            super.onAdImpression();
            sendEvent(RNAdMobGlobals.EVENT_AD_IMPRESSION, null);
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
            sendEvent(RNAdMobGlobals.EVENT_AD_LEFT_APPLICATION, null);
        }
    };


    public void sendEvent(String eventName, WritableMap params) {
        this.reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }


}
