
import React, { useContext } from "react";
import {
  Text,
  View
} from "react-native";
import { NativeAdContext } from "./context";

const AdvertiserView = ( props ) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  return (
    <Text
      {...props}
      nativeID="adAdvertiserView"
    >
      {nativeAd ? nativeAd.advertiser : null}
    </Text>)

}


export default AdvertiserView;
