package com.ammarahmed.rnadmob.nativeads;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class RNAdmobButtonManager extends SimpleViewManager<AppCompatButton> {
    private static final String REACT_CLASS = "RNAdmobButton";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected AppCompatButton createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new AppCompatButton(reactContext);
    }

}