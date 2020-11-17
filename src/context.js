import React from "react";

export const NativeAdContext = React.createContext({
  nativeAd: null,
  nativeAdView: null,
  setNativeAdView: () => {},
  setNativeAd: () => {},
});
