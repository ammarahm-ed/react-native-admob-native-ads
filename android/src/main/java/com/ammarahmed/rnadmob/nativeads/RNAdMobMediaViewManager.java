package com.ammarahmed.rnadmob.nativeads;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.google.android.gms.ads.formats.MediaView;

public class RNAdMobMediaViewManager extends SimpleViewManager<MediaView> {
    private static final String REACT_CLASS = "MediaView";
    private  MediaView mediaView;
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected MediaView createViewInstance(ThemedReactContext reactContext) {
         mediaView = new MediaView(reactContext);
         mediaView.setId((int)762);
         return mediaView;
    }
}

