import React, { createRef, useEffect, useState } from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
} from "react-native";
import { NativeAdContext } from "./context";

const nativeAdView = createRef();

const NativeAdView = (props) => {
  const [nativeAd, setNativeAd] = useState(null);
  
  function updateAd(ad) {
    if (ad) {
      setNativeAd(ad)
    }
  }

  return (
    <NativeAdContext.Provider
      value={{ nativeAd, setNativeAd }}
    >
      <UnifiedNativeAdView
        {...props}
        ref={nativeAdView}
        onUnifiedNativeAdLoaded={event => {
          updateAd(event.nativeEvent)
          if (this.props.onUnifiedNativeAdLoaded) {
            props.onUnifiedNativeAdLoaded(event);
          }
        }}
      />
    </NativeAdContext.Provider>
  );

}

NativeAdView.simulatorId = "SIMULATOR";


const UnifiedNativeAdView = requireNativeComponent(
  "RNGADNativeView",
  NativeAdView
);

export default NativeAdView;
