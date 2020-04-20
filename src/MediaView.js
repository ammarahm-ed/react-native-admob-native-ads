import React, { createRef } from "react";
import { requireNativeComponent, findNodeHandle } from "react-native";
import { nativeAdView } from "./context";

const adMediaView = createRef();

const MediaView = (props) => {
  const _onLayout = () => {
    let handle = findNodeHandle(adMediaView.current);
    nativeAdView.current?.setNativeProps({
      mediaview: handle,
    });
  };

  return (
    <AdMediaView ref={adMediaView} onLayout={_onLayout} style={props.style} />
  );
};

const AdMediaView = requireNativeComponent("MediaView", MediaView);

export default MediaView;
