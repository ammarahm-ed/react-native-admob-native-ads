import React, { useContext, createRef } from "react";
import {
  Text, Platform, findNodeHandle,
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const priceViewRef = createRef();

const PriceView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  
  return (
    <Text
      {...props}
      nativeID="adPriceView"
      ref={priceViewRef}
      onLayout={() => {
        if (Platform.OS === "android") return;
        let handle = findNodeHandle(priceViewRef.current);
        nativeAdView.current?.setNativeProps({
          price: handle
        });
      }}
    >
      {nativeAd ? nativeAd.price : null}
    </Text>
 );
}

export default PriceView;
