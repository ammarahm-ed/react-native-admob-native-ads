package com.ammarahmed.rnadmob.nativeads;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RNAdMobNativePackage implements ReactPackage {

    private RNAdMobNativeViewManager adMobNativeViewManager;
    private RNAdMobMediaViewManager adMobMediaViewManager;

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {

        if (adMobNativeViewManager == null) {
            adMobNativeViewManager = new RNAdMobNativeViewManager();
        }

        return Arrays.<NativeModule>asList(

                new RNNativeAdManager(reactContext,adMobNativeViewManager)

        );
    }

    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {

        if (adMobNativeViewManager == null) {
            adMobNativeViewManager = new RNAdMobNativeViewManager();
        }
      return Arrays.<ViewManager>asList(
        adMobNativeViewManager,
        new RNAdMobMediaViewManager(),
        new RNAdComponentsWrapperManager(),
        new RNAdmobAdChoicesManager()
      );
    }
}
