
import React, { useContext } from "react";
import {
  requireNativeComponent,
} from "react-native";
import { NativeAdContext } from "./context";

const MediaView = () => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);


  return (nativeAd && !nativeAd.video? <View
    style={props.style}
    nativeID="adImageView"
  >
    <Image
      style={props.imageStyle}
      source={{ uri: nativeAd? nativeAd.images[0].uri : null }}
    />
  </View> : <AdMediaView/>
  
  );
}

const AdMediaView = requireNativeComponent(
  "MediaView",
  MediaView
);

export default MediaView;
