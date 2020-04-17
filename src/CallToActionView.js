
import React, { useContext, createRef } from "react";
import {
  Text,
  View,
  findNodeHandle,
  Platform
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";
import { RawButton } from "react-native-gesture-handler";

const callToActionRef = createRef();
const CallToActionView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);


const _onLayout = () => {
  if (Platform.OS === "android") return;
  let handle = findNodeHandle(callToActionRef.current);
  nativeAdView.current?.setNativeProps({
    callToAction: handle
  });
}

  return (
    <RawButton
    nativeID="adCallToAction"
    ref={callToActionRef}
    onLayout={_onLayout}
    style={props.style}
  >
     <Text
      style={[props.textStyle]}
    >
      {nativeAd ? nativeAd.callToAction : null}
    </Text>
   

  </RawButton>);
}

export default CallToActionView;
