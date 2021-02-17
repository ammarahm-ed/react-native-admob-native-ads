import React, { useCallback, useContext, useRef } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const StoreView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const storeViewRef = useRef();
  let nativeTag = null;
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;
    if (nativeAdView._nativeTag === nativeTag) {
      return;
    }
    nativeTag = nativeAdView._nativeTag
    let handle = findNodeHandle(storeViewRef.current);
    nativeAdView.setNativeProps({
      store: handle,
    });
  },[nativeAdView,storeViewRef])



  return (
    <Text {...props} ref={storeViewRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.store : null}
    </Text>
  );
};

export default StoreView;
