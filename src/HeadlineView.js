import React, { useCallback, useContext, useRef } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const HeadlineView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const headlineRef = useRef();
  let nativeTag = null;
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;
    if (nativeAdView._nativeTag === nativeTag) {
      return;
    }
    nativeTag = nativeAdView._nativeTag
    let handle = findNodeHandle(headlineRef.current);
    nativeAdView.setNativeProps({
      headline: handle,
    });
  },[nativeAdView,headlineRef]);

  

  return (
    <Text {...props} ref={headlineRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.headline : null}
    </Text>
  );
};

export default HeadlineView;
