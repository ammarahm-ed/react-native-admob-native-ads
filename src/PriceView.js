import React, { useContext, createRef } from "react";
import { Text, Platform, findNodeHandle } from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const priceViewRef = createRef();

const PriceView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  const _onLayout = () => {
    if (Platform.OS === "android") return;
    let handle = findNodeHandle(priceViewRef.current);
    nativeAdView.current?.setNativeProps({
      price: handle,
    });
  };
  return (
    <Text
      {...props}
      nativeID="adPriceView"
      ref={priceViewRef}
      onLayout={_onLayout}
    >
      {nativeAd ? nativeAd.price : null}
    </Text>
  );
};

export default PriceView;
