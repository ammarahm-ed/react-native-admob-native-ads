
import React, { useContext, createRef } from "react";
import {
  Text,
  View,
  findNodeHandle,
  Platform
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const storeViewRef = createRef();
const StoreView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  return (
    <Text
      {...props}
      nativeID="adStoreView"
      ref={storeViewRef}
      onLayout={() => {
        if (Platform.OS === "android") return;
        let handle = findNodeHandle(storeViewRef.current);
        nativeAdView.current?.setNativeProps({
          store: handle
        });
      }}
    >
      {nativeAd ? nativeAd.store : null}
    </Text>
 );
}

export default StoreView;
