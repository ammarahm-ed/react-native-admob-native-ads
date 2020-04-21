import React, { createRef, useContext } from "react";
import { findNodeHandle, Image } from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const imageViewRef = createRef();

const ImageView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  const _onLayout = () => {
    let handle = findNodeHandle(imageViewRef.current);
    nativeAdView.current?.setNativeProps({
      image: handle,
    });
  };

  return nativeAd && nativeAd.images[0] ? (
    <Image
      {...props}
      ref={imageViewRef}
      onLayout={_onLayout}
      source={{ uri: nativeAd.images[0] }}
    />
  ) : null;
};

export default ImageView;
