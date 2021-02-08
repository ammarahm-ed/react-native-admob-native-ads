import React, { useRef, useEffect, useContext } from "react";
import { findNodeHandle, requireNativeComponent } from "react-native";
import { NativeAdContext } from "./context";

const NativeMediaView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const adMediaView = useRef();

  const _onLayout = () => {
    if (!nativeAdView) return;
    let handle = findNodeHandle(adMediaView.current);
    nativeAdView.setNativeProps({
      mediaview: handle,
    });
  };

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return (
    <AdMediaView ref={adMediaView} onLayout={_onLayout} style={props.style} />
  );
};

const AdMediaView = requireNativeComponent("RNGADMediaView", MediaView);

export default NativeMediaView;
