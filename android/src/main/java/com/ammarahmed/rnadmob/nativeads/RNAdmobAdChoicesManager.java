package com.ammarahmed.rnadmob.nativeads;

import androidx.annotation.NonNull;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.google.android.gms.ads.formats.AdChoicesView;

public class RNAdmobAdChoicesManager extends ViewGroupManager<AdChoicesView> {
    private static final String REACT_CLASS = "AdChoicesView";
    private AdChoicesView adChoicesView;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected AdChoicesView createViewInstance(@NonNull ThemedReactContext reactContext) {
            adChoicesView = new AdChoicesView(reactContext);
            adChoicesView.setId((int) 733);

        return adChoicesView;
    }

}