package com.ammarahmed.rnadmob.nativeads;

import android.view.View;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

public class RNAdComponentsWrapperManager extends ViewGroupManager<RNAdComponentsWrapper> {

    public RNAdComponentsWrapper wrapper;
    public ThemedReactContext mContext;

    public static final String REACT_CLASS = "RNAdComponentWrapper";
    @Override
    public String getName() {
        return REACT_CLASS;
    }


    @Override
    public void addView(RNAdComponentsWrapper parent, View child, int index) {
        super.addView(parent, child, index);
    }

    @Override
    protected RNAdComponentsWrapper createViewInstance(ThemedReactContext reactContext) {
        mContext = reactContext;
        wrapper = new RNAdComponentsWrapper(reactContext);
        return wrapper;
    }

}
