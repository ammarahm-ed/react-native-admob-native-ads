
import React, { useContext } from "react";
import {
  Image,
} from "react-native";
import { NativeAdContext } from "./context";

const ImageView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  return (
    <Image
      style={props.imageStyle}
      nativeID="adImageView"
      source={{ uri: nativeAd? nativeAd.images[0].uri : null }}
    />
 );
}

export default ImageView;
