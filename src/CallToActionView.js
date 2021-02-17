import React, { useCallback, useContext, useRef } from "react";
import { findNodeHandle, Text,View } from "react-native";
import { NativeAdContext } from "./context";
const CallToActionView = ({
  style,
  allowFontScaling = true,
  textStyle,
  allCaps,
}) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const callToActionRef = useRef();
  let nativeTag = null;
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;
    if (nativeAdView._nativeTag === nativeTag) {
      return;
    }
    nativeTag = nativeAdView._nativeTag
    nativeAdView.setNativeProps({
      callToAction: findNodeHandle(callToActionRef.current),
    });
  }, [nativeAdView,callToActionRef]);

  return (
    <View style={style} >
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
    </View>
  );
};

export default CallToActionView;
