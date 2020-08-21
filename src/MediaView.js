import React, { createRef, useEffect, useContext } from "react";
import { findNodeHandle, requireNativeComponent } from "react-native";
import { NativeAdContext } from "./context";

const MediaView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const adMediaView = createRef();

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

const AdMediaView = requireNativeComponent("MediaView", MediaView);

export default MediaView;
