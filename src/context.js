import React from "react";

export const defaultAd = {
  icon:"empty",
  images:[
    {
      "url":null
    }
  ]
}

export const NativeAdContext = React.createContext({
  nativeAd: defaultAd,
  nativeAdView: null,
  setNativeAdView: () => {},
  setNativeAd: () => {},
});
