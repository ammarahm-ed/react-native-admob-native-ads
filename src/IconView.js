import React, { useCallback, useContext, useRef } from "react";
import { findNodeHandle, Image, Platform, requireNativeComponent } from "react-native";
import { NativeAdContext } from "./context";

const IconView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const iconViewRef = useRef();
  let nativeTag = null;
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;
    if (nativeAdView._nativeTag === nativeTag) {
      return;
    }
    nativeTag = nativeAdView._nativeTag
    let handle = findNodeHandle(iconViewRef.current);
    nativeAdView.setNativeProps({
      icon: handle,
    });
  },[nativeAdView,iconViewRef])



  if (nativeAd && nativeAd.icon === "empty") {

    return (
      <GADImageView
        style={props.style}
        ref={iconViewRef}
        onLayout={_onLayout}
      />
    );
  }

  return (
    nativeAd?.icon !== "noicon" && (
      <GADImageView
        {...props}
        resizeMode="cover"
        ref={iconViewRef}
        onLayout={_onLayout}
        source={Platform.OS === "ios" ? null : { uri: nativeAd.icon }}
      />
    )
  );
};

const GADImageView = Platform.OS === "ios" ? requireNativeComponent("RNGADImageView") : Image;

export default IconView;
