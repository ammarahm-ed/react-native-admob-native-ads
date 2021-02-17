import React, { useContext, useRef } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const AdvertiserView = (props) => {
  const { nativeAd, nativeAdView } = useContext(
    NativeAdContext
  );
  const advertiserRef = useRef();
  let nativeTag = null;
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;
    if (nativeAdView._nativeTag === nativeTag) {
      return;
    }
    nativeTag = nativeAdView._nativeTag
    let handle = findNodeHandle(advertiserRef.current);
    nativeAdView.setNativeProps({
      advertiser: handle,
    });
  },[nativeAdView,advertiserRef]);

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
