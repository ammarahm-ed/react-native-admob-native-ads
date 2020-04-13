
import React, { useContext } from "react";
import {
  View,
  Image,
} from "react-native";
import { NativeAdContext } from "./context";

const ImageView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  return (<View
    style={props.style}
    nativeID="adImageView"
  >
    <Image
      style={props.imageStyle}
      source={{ uri: nativeAd? nativeAd.images[0].uri : null }}
    />
  </View>);
}

export default ImageView;
