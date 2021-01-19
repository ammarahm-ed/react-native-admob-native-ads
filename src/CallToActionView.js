import React, { useContext, useEffect, useRef, useCallback } from "react";
import { findNodeHandle, Text, View } from "react-native";
import { NativeAdContext } from "./context";

const CallToActionView = ({
  style,
  allowFontScaling = true,
  textStyle,
  allCaps,
}) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const callToActionRef = useRef();

  const _onLayout = useCallback(() => {
    if (!nativeAdView) {
      return;
    }
    nativeAdView.setNativeProps({
      callToAction: findNodeHandle(callToActionRef.current),
    });
  }, [nativeAdView]);

  useEffect(() => {
    _onLayout();
  }, [_onLayout]);

  return (
    <View style={style} onLayout={_onLayout}>
      <Text
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
    </View>
  );
};

export default CallToActionView;
