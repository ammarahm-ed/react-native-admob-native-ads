
import React, { useContext } from "react";
import {
  Text,
  View
} from "react-native";
import { NativeAdContext } from "./context";

const TaglineView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  return (
    <Text
      {...props}
      nativeID="adTaglineView"
    >
      {nativeAd ? nativeAd.body : null}
    </Text>
  );
}

export default TaglineView;
