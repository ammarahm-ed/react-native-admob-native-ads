package com.ammarahmed.rnadmob.nativeads;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class RNNativeAdManager extends ReactContextBaseJavaModule {

    private RNAdMobNativeViewManager nativeViewManager;

    @NonNull
    @Override
    public String getName() {
        return "NativeAdManager";
    }

    public RNNativeAdManager(ReactApplicationContext reactApplicationContext, RNAdMobNativeViewManager rnAdMobNativeViewManager) {
        super(reactApplicationContext);

        nativeViewManager = rnAdMobNativeViewManager;

    }


    @ReactMethod
    public void registerViewsForAd(final ReadableMap viewIds) {


    }





}
