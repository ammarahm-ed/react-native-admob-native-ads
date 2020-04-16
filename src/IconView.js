
import React, { useContext } from "react";
import {
  Image,
} from "react-native";
import { NativeAdContext } from "./context";

const IconView = ( props ) => {
  const {nativeAd, setNativeAd} = useContext(NativeAdContext);
  return (
    <Image
      {...props}
      resizeMode="cover"
      nativeID="adIconView"
      source={{uri:nativeAd? nativeAd.icon.uri : null}}
    />
    
    );
}

export default IconView;
