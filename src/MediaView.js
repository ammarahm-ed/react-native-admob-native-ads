import React, { createRef, useContext } from "react";
import { findNodeHandle, requireNativeComponent } from "react-native";
import { NativeAdContext, nativeAdView } from "./context";
import ImageView from "./ImageView";

const adMediaView = createRef();

const MediaView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  const _onLayout = () => {
    let handle = findNodeHandle(adMediaView.current);
    nativeAdView.current?.setNativeProps({
      mediaview: handle,
    });  
  };
  return (nativeAd && !nativeAd.video? <ImageView
  style={
    props.style
  }
  /> : <AdMediaView 
    style={props.style}
    ref={adMediaView} onLayout={_onLayout} />
  );
};

const AdMediaView = requireNativeComponent("MediaView", MediaView);

export default MediaView;
