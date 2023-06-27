import React from "react";

export const defaultAd = {
  icon: undefined,
  images: [
    {
      url: null,
    },
  ],
};

export const NativeAdContext = React.createContext({
  nativeAd: defaultAd,
  nativeAdView: null,
  setNativeAdView: () => {},
  setNativeAd: () => {},
});
