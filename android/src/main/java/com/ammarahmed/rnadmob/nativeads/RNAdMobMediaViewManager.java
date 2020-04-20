package com.ammarahmed.rnadmob.nativeads;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class RNAdMobMediaViewManager extends ViewGroupManager<RNMediaView> {
    private static final String REACT_CLASS = "MediaView";
    private  RNMediaView mediaView;



    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected RNMediaView createViewInstance(ThemedReactContext reactContext) {

        mediaView = new RNMediaView(reactContext);

        return mediaView;
    }

}

