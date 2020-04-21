import React, { createRef, useContext } from "react";
import { findNodeHandle, Text } from "react-native";
import { RawButton } from "react-native-gesture-handler";
import { NativeAdContext, nativeAdView } from "./context";

const callToActionRef = createRef();
const CallToActionView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  const _onLayout = () => {
    let handle = findNodeHandle(callToActionRef.current);
    nativeAdView.current?.setNativeProps({
      callToAction: handle,
    });
  };

  return (
    <RawButton
      ref={callToActionRef}
      onLayout={_onLayout}
      style={[
        {
          paddingVertical: 8,
          paddingHorizontal: 12,
          backgroundColor: "#5DADE2",
          justifyContent: "center",
          alignItems: "center",
          borderRadius: 5,
          elevation: 10,
        },
        props.style,
      ]}
    >
      <Text style={[props.textStyle]}>
        {nativeAd
          ? props.allCaps
            ? nativeAd.callToAction?.toUpperCase()
            : nativeAd.callToAction
          : null}
      </Text>
    </RawButton>
  );
};

export default CallToActionView;
