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
    <View
      style={{
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <ButtonView
        style={style}
        activeOpacity={0.85}
        buttonAndroidStyle={
          Platform.OS === "android" ? buttonAndroidStyle : null
        }
        ref={callToActionRef}
        onLayout={_onLayout}
      >
        {Platform.OS !== "android" && (
          <Text allowFontScaling={allowFontScaling} style={textStyle}>
            {nativeAd
              ? allCaps
                ? nativeAd.callToAction?.toUpperCase()
                : nativeAd.callToAction
              : null}
          </Text>
        )}
      </ButtonView>

      {Platform.OS === "android" && (
        <View
          style={{
            position: "absolute",
            zIndex: 10,
            elevation: style.elevation ? style.elevation + 10 : 2,
          }}
          pointerEvents="none"
        >
          <Text allowFontScaling={allowFontScaling} style={textStyle}>
            {nativeAd
              ? allCaps
                ? nativeAd.callToAction?.toUpperCase()
                : nativeAd.callToAction
              : null}
          </Text>
        </View>
      )}
    </View>
  );
};

const ButtonView =
  Platform.OS === "android"
    ? requireNativeComponent("RNAdmobButton")
    : TouchableOpacity;

export default CallToActionView;
