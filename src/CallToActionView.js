
import React, { useContext, createRef } from "react";
import {
  Text,
  View,
  findNodeHandle,
  Platform
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

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

  return (<View
    style={props.style} >
    <Text
      nativeID="adCallToAction"
      ref={callToActionRef}
      onLayout={_onLayout}
      style={[props.textStyle]}
    >
      {nativeAd ? nativeAd.callToAction : null}
    </Text>

  </View>);
}

export default CallToActionView;
