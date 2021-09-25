package com.ammarahmed.rnadmob.nativeads;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class RNAdmobButtonManager extends SimpleViewManager<RNAdmobButton> {
    private static final String REACT_CLASS = "RNAdmobButton";
    public static final String PROP_TITLE = "title";
    public static final String PROP_BUTTON_ANDROID_STYLE = "buttonAndroidStyle";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @NonNull
    @Override
    protected RNAdmobButton createViewInstance(@NonNull ThemedReactContext reactContext) {

        return new RNAdmobButton(reactContext);
    }


    @ReactProp(name = PROP_BUTTON_ANDROID_STYLE)
    public void setButtonAndroidStyle(RNAdmobButton button, @Nullable ReadableMap style) {
        if (style == null) return;
        String color = null;
        String backgroundColor = null;
        String borderColor = null;
        int borderWidth = 0;
        int borderRadius= 0;
        int fontSize=16;

        if (style.hasKey("color")) {
            color  =  style.getString("color");
        }

        if (style.hasKey("backgroundColor")) {
            backgroundColor  =  style.getString("backgroundColor");
        }

        if (style.hasKey("borderColor")) {
            borderColor  =  style.getString("borderColor");
        }

        if (style.hasKey("borderWidth")) {
            if (style.getType("borderWidth") == ReadableType.Number) {
                borderWidth  =  style.getInt("borderWidth");
            }

        }

        if (style.hasKey("borderRadius")) {
            if (style.getType("borderRadius") == ReadableType.Number) {
                borderRadius  =  style.getInt("borderRadius");
            }

        }

        if (style.hasKey("fontSize")) {
            if (style.getType("fontSize") == ReadableType.Number) {
                fontSize  =  style.getInt("fontSize");
                button.setTextSize(fontSize);
            }

        }



        button.setButtonStyle(color,backgroundColor,borderColor,borderWidth,borderRadius);
    }


    @ReactProp(name = PROP_TITLE)
    public void setTitle(RNAdmobButton button, String title) {
        button.setText(title);
    }



}