
import React, { useContext, createRef } from "react";
import {
  Image,findNodeHandle, Platform
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const iconViewRef = createRef();

const IconView = ( props ) => {
  const {nativeAd, setNativeAd} = useContext(NativeAdContext);
  return (
    nativeAd && nativeAd.icon? 
    <Image
      {...props}
      resizeMode="cover"
      ref={iconViewRef}
      onLayout={()=> {
        if (Platform.OS === "android") return;
        let handle = findNodeHandle(iconViewRef.current);
        nativeAdView.current?.setNativeProps({
          icon:handle
        });
      }}
      nativeID="adIconView"
      source={{uri:nativeAd.icon}}
    /> : null
    
    );
}

export default IconView;
