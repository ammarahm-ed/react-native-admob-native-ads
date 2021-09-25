package com.ammarahmed.rnadmob.nativeads;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.facebook.react.bridge.ReactContext;


public class RNAdmobButton extends AppCompatButton {

    private String mBgBackground = "#ffffff";
    private String mBgBorderColor = "#ffffff";
    private int mBgBorderRadius = 0;
    private int mBgBorderWidth = 0;
    private String mTextColor = "#ffffff";
    private String mBackgroundColor = "#0077cc";
    private String mBorderColor = "#0077cc";
    private int mBorderWidth = 0;
    private int mBorderRadius = 0;

    GradientDrawable gradientDrawableA;
    GradientDrawable gradientDrawableB;

    public RNAdmobButton(ReactContext context) {
        super(context);
        requestLayout();
    }



    public void setButtonStyle(@Nullable String textColor, @Nullable String backgroundColor, @Nullable String borderColor, @Nullable int borderWidth, @Nullable int borderRadius) {

        gradientDrawableA = new GradientDrawable();
        gradientDrawableB = new GradientDrawable();

        if (textColor != null) {
            mTextColor = textColor;
            int tc = Color.parseColor(textColor);
            this.setTextColor(tc);

        }

        if (backgroundColor != null) {
            mBackgroundColor = backgroundColor;

            int bc = Color.parseColor(backgroundColor);
            gradientDrawableA.setColor(bc);
            gradientDrawableB.setColor(bc);
        }
        if (borderRadius > 0) {
            mBorderRadius = borderRadius;
            gradientDrawableA.setCornerRadius(borderRadius* 3);
            gradientDrawableB.setCornerRadius(borderRadius * 3);
        }
        if (borderWidth > 0) {
            mBorderWidth = borderWidth;
            int brc = Color.parseColor("#000000");
            if (borderColor != null) {
                mBorderColor = borderColor;
                brc = Color.parseColor(borderColor);
            }

            gradientDrawableA.setStroke(borderWidth, brc);
            gradientDrawableB.setStroke(borderWidth, brc);

        }
        this.setBackground(gradientDrawableA);
    }

}