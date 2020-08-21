import React, { createRef, useContext, useEffect } from "react";
import { findNodeHandle, Image } from "react-native";
import { NativeAdContext } from "./context";

const IconView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
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

  return nativeAd &&
    nativeAd.icon &&
    nativeAd.icon !== "empty" &&
    nativeAd.icon !== "noicon" ? (
    <Image
      {...props}
      resizeMode="cover"
      ref={iconViewRef}
      onLayout={_onLayout}
      source={{ uri: nativeAd.icon }}
    />
  ) : nativeAd && nativeAd.icon && nativeAd.icon === "noicon" ? null : (
    <Image
      {...props}
      resizeMode="cover"
      ref={iconViewRef}
      onLayout={_onLayout}
    />
  );
};

export default IconView;
