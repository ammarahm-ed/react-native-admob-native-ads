import React, { useCallback, useContext, useEffect, useRef } from "react";
import {
  findNodeHandle,
  Platform,
  requireNativeComponent,
  StyleSheet,
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

  const renderText = (
    <Text allowFontScaling={allowFontScaling} style={textStyle}>
      {nativeAd
        ? allCaps
          ? nativeAd.callToAction?.toUpperCase()
          : nativeAd.callToAction
        : null}
    </Text>
  );

  return (
    <View style={styles.container}>
      <ButtonView
        style={style}
        activeOpacity={0.85}
        buttonAndroidStyle={
          Platform.OS === "android" ? buttonAndroidStyle : null
        }
        ref={callToActionRef}
        onLayout={_onLayout}
      >
        {Platform.OS !== "android" && renderText}
      </ButtonView>

      {Platform.OS === "android" && (
        <View
          style={[
            styles.textwrapper,
            {
              elevation: style.elevation ? style.elevation + 10 : 10,
            },
          ]}
          pointerEvents="none"
        >
          {renderText}
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    justifyContent: "center",
    alignItems: "center",
  },
  textwrapper: {
    position: "absolute",
    zIndex: 10,
  },
});

const ButtonView =
  Platform.OS === "android"
    ? requireNativeComponent("RNAdmobButton")
    : TouchableOpacity;

export default CallToActionView;
