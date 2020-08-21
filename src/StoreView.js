import React, { createRef, useContext, useEffect } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const StoreView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const storeViewRef = createRef();
  const _onLayout = () => {
    if (!nativeAdView) return;
    let handle = findNodeHandle(storeViewRef.current);
    nativeAdView.setNativeProps({
      store: handle,
    });
  };

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
