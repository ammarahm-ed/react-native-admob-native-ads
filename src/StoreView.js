import React, { useContext, createRef } from "react";
import { Text, findNodeHandle, Platform } from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const storeViewRef = createRef();
const StoreView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  const _onLayout = () => {
    let handle = findNodeHandle(storeViewRef.current);
    nativeAdView.current?.setNativeProps({
      store: handle,
    });
  };

  return (
    <Text {...props} ref={storeViewRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.store : null}
    </Text>
  );
};

export default StoreView;
