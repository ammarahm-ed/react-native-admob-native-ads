import React, { useCallback, useContext, useEffect, useRef } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const HeadlineView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const headlineRef = useRef();
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;

    let handle = findNodeHandle(headlineRef.current);
    nativeAdView.setNativeProps({
      headline: handle,
    });
  }, [nativeAdView, headlineRef]);

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return (
    <Text {...props} ref={headlineRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.headline : null}
    </Text>
  );
};

export default HeadlineView;
