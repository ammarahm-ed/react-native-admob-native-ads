
import React, { useContext } from "react";
import {
  View,
  Image
} from "react-native";
import { NativeAdContext } from "./context";

const IconView = ({ props }) => {
  const {nativeAd, setNativeAd} = useContext(NativeAdContext);

  return (<View
    style={props.style}
    nativeID="adIconView"
  >
    <Image
      style={[{ width: 128, height: 128 }, props.imageStyle]}
      source={{uri:nativeAd.icon.uri}}
    />
  </View>);
}

export default IconView;
