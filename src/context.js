import React, { createRef } from "react";

export const nativeAdView = createRef();

export const NativeAdContext = React.createContext({
  nativeAd: null,
  setNativeAd: () => {},
});
