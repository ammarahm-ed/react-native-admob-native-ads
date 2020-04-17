
import React, { useContext, createRef } from "react";
import {
  Text,
  Platform,
  findNodeHandle
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";
const advertiserRef = createRef();

const AdvertiserView = ( props ) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  return (
    <Text
      {...props}
      nativeID="adAdvertiserView"
      onLayout={() => {
        if (Platform.OS === "android") return;
        let handle = findNodeHandle(advertiserRef.current);
        nativeAdView.current?.setNativeProps({
          advertiser: handle
        });
      }}
    >
      {nativeAd ? nativeAd.advertiser : null}
    </Text>)

}


export default AdvertiserView;
