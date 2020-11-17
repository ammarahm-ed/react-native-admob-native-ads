import React, { useContext, useEffect,useRef, useCallback } from "react";
import { findNodeHandle, Text, View } from "react-native";
import { NativeAdContext } from "./context";

const CallToActionView = (props) => {
  const {nativeAd, nativeAdView} = useContext(NativeAdContext);
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
      <View
        style={props.style}
        onLayout={_onLayout}>
        <Text
          ref={callToActionRef}
          allowFontScaling={
            props.allowFontScaling ? props.allowFontScaling : false
          }
          style={props.textStyle}
        >
          {nativeAd
            ? props.allCaps
              ? nativeAd.callToAction?.toUpperCase()
              : nativeAd.callToAction
            : null}
        </Text>
      </View>
  );
};

export default CallToActionView;
