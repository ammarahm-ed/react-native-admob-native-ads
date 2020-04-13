
import React, { useContext } from "react";
import {
  Text,
  View
} from "react-native";
import { NativeAdContext } from "./context";

const StoreView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  return (
    <Text
      {...props}
      nativeID="adStoreView"
    >
      {nativeAd ? nativeAd.store : null}
    </Text>
 );
}

export default StoreView;
