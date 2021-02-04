import React, { useRef, useContext, useEffect } from "react";
import { findNodeHandle, Image, Platform, requireNativeComponent } from "react-native";
import { NativeAdContext } from "./context";

const IconView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const iconViewRef = useRef();

  const _onLayout = () => {
    if (!nativeAdView) return;
    let handle = findNodeHandle(iconViewRef.current);
    nativeAdView.setNativeProps({
      icon: handle,
    });
  };

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);


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
        source={{ uri: nativeAd.icon }}
      />
    )
  );
};

const GADImageView = Platform.OS === "ios" ? requireNativeComponent("RNGADImageView") : Image;

export default IconView;
