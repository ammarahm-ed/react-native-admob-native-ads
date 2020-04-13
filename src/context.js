import React from "react";


export const nativeAdView = createRef();

export const NativeAdContext = React.createContext({
  nativeAd: null,
  setNativeAd: () => { }
});