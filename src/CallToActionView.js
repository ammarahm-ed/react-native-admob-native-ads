import React, { createRef, useContext, useEffect } from "react";
import { findNodeHandle, Text, View } from "react-native";
import { NativeAdContext } from "./context";

const CallToActionView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const callToActionRef = createRef();
  const _onLayout = () => {
    if (!nativeAdView) return;
    let handle = findNodeHandle(callToActionRef.current);
    nativeAdView.setNativeProps({
      callToAction: handle,
    });
  };

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

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
