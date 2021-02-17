import React, { useContext, useRef } from "react";
import { findNodeHandle, Image } from "react-native";
import { NativeAdContext } from "./context";

const ImageView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const imageViewRef = useRef();
  let nativeTag = null;
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;
    if (nativeAdView._nativeTag === nativeTag) {
      return;
    }
    nativeTag = nativeAdView._nativeTag
    let handle = findNodeHandle(imageViewRef.current);
    nativeAdView.setNativeProps({
      image: handle,
    });
  },[nativeAdView,imageViewRef])


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
