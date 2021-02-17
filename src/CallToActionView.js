import React, { useCallback, useContext, useEffect, useRef } from "react";
import {
  findNodeHandle,
  Platform,
  requireNativeComponent,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { NativeAdContext } from "./context";
const CallToActionView = ({
  style,
  allowFontScaling = true,
  textStyle,
  allCaps,
  buttonAndroidStyle,
}) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const callToActionRef = useRef();
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;

    nativeAdView.setNativeProps({
      callToAction: findNodeHandle(callToActionRef.current),
    });
  }, [nativeAdView, callToActionRef]);

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return (
    <ButtonView
      style={style}
      title={
        nativeAd && Platform.OS === "android"
          ? allCaps
            ? nativeAd.callToAction?.toUpperCase()
            : nativeAd.callToAction
          : null
      }
      activeOpacity={0.85}
      buttonAndroidStyle={Platform.OS === "android" ? buttonAndroidStyle : null}
      ref={callToActionRef}
      onLayout={_onLayout}
    >
      {Platform.OS !== "android" && (
        <Text
          onLayout={_onLayout}
          ref={callToActionRef}
          allowFontScaling={allowFontScaling}
          style={textStyle}
        >
          {nativeAd
            ? allCaps
              ? nativeAd.callToAction?.toUpperCase()
              : nativeAd.callToAction
            : null}
        </Text>
      )}
    </ButtonView>
  );
};

const ButtonView =
  Platform.OS === "android"
    ? requireNativeComponent("RNAdmobButton")
    : TouchableOpacity;

export default CallToActionView;
