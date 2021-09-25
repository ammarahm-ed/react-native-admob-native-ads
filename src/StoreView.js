import React, { useCallback, useContext, useEffect, useRef } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const StoreView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const storeViewRef = useRef();
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;

    let handle = findNodeHandle(storeViewRef.current);
    nativeAdView.setNativeProps({
      store: handle,
    });
  }, [nativeAdView, storeViewRef]);

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return (
    <Text {...props} ref={storeViewRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.store : null}
    </Text>
  );
};

export default StoreView;
