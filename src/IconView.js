import React, { createRef, useContext, useEffect } from "react";
import { findNodeHandle, Image } from "react-native";
import { NativeAdContext } from "./context";

const IconView = (props) => {
  const { nativeAd, nativeAdView, setNativeAdView, setNativeAd } = useContext(
    NativeAdContext
  );
  const iconViewRef = createRef();

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

  return nativeAd && nativeAd.icon ? (
    <Image
      {...props}
      resizeMode="cover"
      ref={iconViewRef}
      onLayout={_onLayout}
      source={{ uri: nativeAd.icon }}
    />
  ) : null;
};

export default IconView;
