import React, { createRef, useContext, useEffect } from "react";
import { findNodeHandle, Text } from "react-native";
import {
  RawButton,
  GestureHandlerRootView,
} from "react-native-gesture-handler";
import { NativeAdContext } from "./context";

const CallToActionView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const callToActionRef = createRef();
  const _onLayout = () => {
    if (!nativeAdView) return;
    let handle = findNodeHandle(callToActionRef.current);
    nativeAdView.setNativeProps({
      callToAction: handle,
    });
  };

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return (
    <GestureHandlerRootView>
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
        <Text
          allowFontScaling={
            props.allowFontScaling ? props.allowFontScaling : false
          }
          style={[props.textStyle]}
        >
          {nativeAd
            ? props.allCaps
              ? nativeAd.callToAction?.toUpperCase()
              : nativeAd.callToAction
            : null}
        </Text>
      </RawButton>
    </GestureHandlerRootView>
  );
};

export default CallToActionView;
