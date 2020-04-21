import React, { createRef, useContext } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const priceViewRef = createRef();

const PriceView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  const _onLayout = () => {
    let handle = findNodeHandle(priceViewRef.current);
    nativeAdView.current?.setNativeProps({
      price: handle,
    });
  };
  return (
    <Text {...props} ref={priceViewRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.price : null}
    </Text>
  );
};

export default PriceView;
