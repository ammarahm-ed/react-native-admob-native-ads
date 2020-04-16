
import React, { useContext, useEffect, createRef } from "react";
import {
  requireNativeComponent,findNodeHandle
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const adMediaView = createRef();

const MediaView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  useEffect(() => {

    let handle = findNodeHandle(adMediaView.current);
    nativeAdView.current?.setNativeProps({
      adMediaView:handle
    });
    
  },[nativeAd])

  return ( <AdMediaView 

    ref={adMediaView}
    onLayout={event => {
      //console.log(event.);
    }}
    style={props.style}
  />
  );
}

const AdMediaView = requireNativeComponent(
  "MediaView",
  MediaView
);

export default MediaView;
