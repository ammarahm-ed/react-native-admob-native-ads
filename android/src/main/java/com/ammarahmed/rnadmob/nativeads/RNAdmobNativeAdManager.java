package com.ammarahmed.rnadmob.nativeads;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;

import java.util.Map;

public class RNAdmobNativeAdManager extends ReactContextBaseJavaModule {

    public RNAdmobNativeAdManager(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @Override
    public String getName() {
        return "RNAdmobNativeAdsManager";
    }

    public void setRequestConfiguration(ReadableMap config, Promise promise) {
        Context context = getReactApplicationContext().getCurrentActivity();
        if (context == null) {
            Log.e("AdmobNativeAds", "setRequestConfiguration() is called outside MainActivity");
            context = getReactApplicationContext();
        }
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
             configuration.setTestDeviceIds(Arguments.toList(config.getArray("testDeviceIds")));
        }

        MobileAds.setRequestConfiguration(configuration.build());
        MobileAds.initialize(context, (InitializationStatus status) -> {
            WritableMap map = Arguments.createMap();
            for (Map.Entry<String, AdapterStatus> entry: status.getAdapterStatusMap().entrySet()) {
                WritableMap info = Arguments.createMap();
                info.putString("initialization_state", entry.getValue().getInitializationState().toString());
                info.putString("description", entry.getValue().getDescription());
                map.putMap(entry.getKey(), info);
            }
            promise.resolve(map);
        });
    }

    @ReactMethod
    public void isTestDevice(Promise promise) {
        AdRequest builder = new AdRequest.Builder().build();
        promise.resolve(builder.isTestDevice(getReactApplicationContext()));
    }
}
