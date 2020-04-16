
import React, { useContext, useEffect, useState } from "react";
import {
  Image,
  TouchableWithoutFeedback
} from "react-native";
import { NativeAdContext } from "./context";

const IconView = ( props ) => {
  const {nativeAd, setNativeAd} = useContext(NativeAdContext);

  const [update,setUpdate] = useState(5);

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
