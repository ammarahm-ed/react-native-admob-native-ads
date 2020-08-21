import React, { createRef, useContext, useEffect } from "react";
import { findNodeHandle, Image } from "react-native";
import { NativeAdContext } from "./context";

const ImageView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const imageViewRef = createRef();
  const _onLayout = () => {
    if (!nativeAdView) return;
    let handle = findNodeHandle(imageViewRef.current);
    nativeAdView.setNativeProps({
      image: handle,
    });
  };

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return nativeAd && nativeAd.images && nativeAd.images[0] ? (
    <Image
      {...props}
      ref={imageViewRef}
      onLayout={_onLayout}
      source={{ uri: nativeAd.images[0].url }}
    />
  ) : null;
};

export default ImageView;
