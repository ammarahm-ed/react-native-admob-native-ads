import React, { createRef, useContext, useEffect } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const AdvertiserView = (props) => {
  const { nativeAd, nativeAdView } = useContext(
    NativeAdContext
  );
  const advertiserRef = createRef();

  const _onLayout = () => {
    if (!nativeAdView) return;
    let handle = findNodeHandle(advertiserRef.current);
    nativeAdView.setNativeProps({
      advertiser: handle,
    });
  };
  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return (
    <Text {...props} nativeID="adAdvertiserView" onLayout={_onLayout}>
      {nativeAd
        ? props.allCaps
          ? nativeAd.advertiser?.toUpperCase()
          : nativeAd.advertiser
        : null}
    </Text>
  );
};

export default AdvertiserView;
