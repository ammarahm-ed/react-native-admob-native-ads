package com.ammarahmed.rnadmob.nativeads;

import android.view.View;

import androidx.annotation.NonNull;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.google.android.gms.ads.nativead.AdChoicesView;

public class RNAdmobAdChoicesManager extends ViewGroupManager<AdChoicesView> {
    private static final String REACT_CLASS = "AdChoicesView";
    private AdChoicesView adChoicesView;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public void addView(AdChoicesView parent, View child, int index) {
        super.addView(parent, child, index);
    }

    @NonNull
    @Override
    protected AdChoicesView createViewInstance(@NonNull ThemedReactContext reactContext) {
            adChoicesView = new AdChoicesView(reactContext);

        return adChoicesView;
    }

}