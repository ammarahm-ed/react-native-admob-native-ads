
import React, { useContext } from "react";
import {
  Text,
  View
} from "react-native";
import { NativeAdContext } from "./context";

const StoreView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  return (<View
    style={props.style}
    nativeID="adHeadlineView"
  >
    <Text
      textStyle={props.textStyle}
    >
      {nativeAd ? nativeAd.store : null}
    </Text>
  </View>);
}

export default StoreView;
