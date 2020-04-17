import React, { useContext, createRef } from "react";
import { Image, findNodeHandle, Platform } from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const imageViewRef = createRef();

const ImageView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  const _onLayout = () => {
    if (Platform.OS === "android") return;
    let handle = findNodeHandle(imageViewRef.current);
    nativeAdView.current?.setNativeProps({
      image: handle,
    });
  };

  return nativeAd && nativeAd.images[0] ? (
    <Image
      style={props.imageStyle}
      ref={imageViewRef}
      onLayout={_onLayout}
      nativeID="adImageView"
      source={{ uri: nativeAd.images[0] }}
    />
  ) : null;
};

export default ImageView;
