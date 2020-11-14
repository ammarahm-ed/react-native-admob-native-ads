package com.ammarahmed.rnadmob.nativeads;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableNativeArray;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.ArrayList;
import java.util.List;

public class RNAdmobNativeAdsManager extends ReactContextBaseJavaModule {
    public ReactApplicationContext mContext;
    public RNAdmobNativeAdsManager(ReactApplicationContext context) {
        super(context);
        mContext = context;
        MobileAds.initialize(context);
        System.out.println("younes ad manager constructed");
    }

    @NonNull
    @Override
    public String getName() {
        return "RNAdmobNativeAdsManager";
    }

    @ReactMethod
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
        MobileAds.initialize(context, (InitializationStatus status) -> {
            WritableArray array = Arguments.createArray();
            for (Map.Entry<String, AdapterStatus> entry: status.getAdapterStatusMap().entrySet()) {
                WritableMap info = Arguments.createMap();
                info.putString("name", entry.getKey());
                info.putInt("state", entry.getValue().getInitializationState().ordinal());
                info.putString("description", entry.getValue().getDescription());
                array.pushMap(info);
            }
            promise.resolve(array);
        });
    }

    @ReactMethod
    public void isTestDevice(Promise promise) {
        AdRequest builder = new AdRequest.Builder().build();
        promise.resolve(builder.isTestDevice(getReactApplicationContext()));
    }

    @ReactMethod
    public void loadNativeAds(ReadableMap config){
        Constants.cacheManager.registerAd(mContext, config);
    }

//    @ReactMethod
//    public void printAds(){
//        Constants.cacheManager.printAds();
//    }
//
//    @ReactMethod
//    public void hasLoadedAd(String id, Promise promise) {
//        promise.resolve(Constants.cacheManager.hasLoadedAd(id));
//    }

}
