
import React from "react";
import {
  Text,
  View
} from "react-native";
import { NativeAdContext } from "./context";

const TaglineView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  return (<View
    style={props.style}
    nativeID="adBodyView"
  >
    <Text
      textStyle={props.textStyle}
    >
      {nativeAd ? nativeAd.body : null}
    </Text>
  </View>);
}

export default TaglineView;
