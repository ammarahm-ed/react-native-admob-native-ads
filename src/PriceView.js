import React, { useContext } from "react";
import {
  Text,
} from "react-native";
import { NativeAdContext } from "./context";

const PriceView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  
  return (
    <Text
      {...props}
      nativeID="adPriceView"
    >
      {nativeAd ? nativeAd.price : null}
    </Text>
 );
}

export default PriceView;
