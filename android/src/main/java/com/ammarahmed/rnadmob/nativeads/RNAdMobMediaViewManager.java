package com.ammarahmed.rnadmob.nativeads;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class RNAdMobMediaViewManager extends ViewGroupManager<RNMediaView> {
    private static final String REACT_CLASS = "RNGADMediaView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNMediaView createViewInstance(ThemedReactContext reactContext) {
        return new RNMediaView(reactContext);
    }

}

