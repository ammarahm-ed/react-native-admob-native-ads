package com.ammarahmed.rnadmob.nativeads;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeArray;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RNAdmobNativeAdsManager extends ReactContextBaseJavaModule {

    private ReactContext reactContext;

    public RNAdmobNativeAdsManager(ReactApplicationContext rc) {
        super(rc);
        reactContext = rc;

    }

    @Override
    public String getName() {
        return "RNAdmobNativeAdsManager";
    }




    @ReactMethod
    public void setRequestConfiguration(ReadableMap config) {


        RequestConfiguration.Builder configuration = new RequestConfiguration.Builder();

        if (config.hasKey("maxAdContentRating")) {
            if (config.getString("maxAdContentRating") != null) {
                configuration.setMaxAdContentRating(config.getString("maxAdContentRating"));
            }
        }

        if (config.hasKey("tagForChildDirectedTreatment")) {
            configuration.setTagForChildDirectedTreatment(config.getInt("tagForChildDirectedTreatment"));
        }
        if (config.hasKey("tagForUnderAgeOfConsent")) {
            configuration.setTagForUnderAgeOfConsent(config.getInt("TagForUnderAgeOfConsent"));
        }
        if (config.hasKey("testDeviceIds")) {
            ReadableNativeArray nativeArray = (ReadableNativeArray) config.getArray("testDeviceIds");
            ArrayList<Object> list = nativeArray.toArrayList();
            List<String> testDeviceIds = Arrays.asList(list.toArray(new String[list.size()]));
            configuration.setTestDeviceIds(testDeviceIds);
        }

        MobileAds.setRequestConfiguration(configuration.build());
        MobileAds.initialize(reactContext);

    }



    @ReactMethod
    public void isTestDevice(Promise promise) {

         AdRequest builder =   new AdRequest.Builder().build();
         if (builder != null) {
             promise.resolve(builder.isTestDevice(reactContext));
         }
    }
}
