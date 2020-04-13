
import React, { useContext } from "react";
import {
  Text,
  TouchableOpacity
} from "react-native";
import { NativeAdContext } from "./context";

const CallToActionView = ({ props }) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);


  return (<TouchableOpacity
    style={props.style}
    nativeID="adCallToAction"
  >
    <Text
      textStyle={props.textStyle}
    >
      {nativeAd? nativeAd.callToAction : null}
    </Text>
  </TouchableOpacity>);
}

export default CallToActionView;
