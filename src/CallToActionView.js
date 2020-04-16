
import React, { useContext } from "react";
import {
  Text,
  View
} from "react-native";
import { NativeAdContext } from "./context";
const CallToActionView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);



  return (<View
    style={props.style}

  >
    <Text
      nativeID="adCallToAction"
      style={[props.textStyle]}
    >
      {nativeAd ? nativeAd.callToAction : null}
    </Text>

  </View>);
}

export default CallToActionView;
