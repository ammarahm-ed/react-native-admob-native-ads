import React, { useContext, useEffect, useRef,useCallback } from "react";
import { findNodeHandle, Image } from "react-native";
import { NativeAdContext } from "./context";

const ImageView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const imageViewRef = useRef();
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;

    let handle = findNodeHandle(imageViewRef.current);
    nativeAdView.setNativeProps({
      image: handle,
    });
  }, [nativeAdView, imageViewRef]);

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return nativeAd?.images[0] ? (
    <Image
      {...props}
      ref={imageViewRef}
      onLayout={_onLayout}
      source={{ uri: nativeAd.images[0].url }}
    />
  ) : null;
};

export default ImageView;
