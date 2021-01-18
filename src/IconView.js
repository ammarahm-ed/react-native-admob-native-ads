import React, { useRef, useContext, useEffect } from "react";
import { findNodeHandle, Image } from "react-native";
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
      <Image
        {...props}
        resizeMode="cover"
        ref={iconViewRef}
        onLayout={_onLayout}
      />
    );
  }

  return (
    nativeAd?.icon !== "noicon" && (
      <Image
        {...props}
        resizeMode="cover"
        ref={iconViewRef}
        onLayout={_onLayout}
        source={{ uri: nativeAd.icon }}
      />
    )
  );
};

export default IconView;
