import React, { useContext, useRef } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const PriceView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const priceViewRef = useRef();
  let nativeTag = null;
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;
    if (nativeAdView._nativeTag === nativeTag) {
      return;
    }
    nativeTag = nativeAdView._nativeTag
    let handle = findNodeHandle(priceViewRef.current);
    nativeAdView.setNativeProps({
      price: handle,
    });
  },[nativeAdView,priceViewRef])


  return (
    <Text {...props} ref={priceViewRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.price : null}
    </Text>
  );
};

export default PriceView;
