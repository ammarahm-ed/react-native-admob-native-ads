package com.ammarahmed.rnadmob.nativeads;


import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
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
    private int numberOfAdsToLoad = 2;
    private String adUnitId = null;
    private boolean requestNonPersonalizedAdsOnly = false;

    public RNAdmobNativeAdsManager(ReactApplicationContext context) {
        super(context);
        reactContext = context;
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


}
