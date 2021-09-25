import React, { useCallback, useContext, useEffect, useRef } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const TaglineView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const taglineRef = useRef();
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;

    let handle = findNodeHandle(taglineRef.current);
    nativeAdView.setNativeProps({
      tagline: handle,
    });
  }, [nativeAdView, taglineRef]);

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return (
    <Text {...props} ref={taglineRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.tagline : null}
    </Text>
  );
};

export default TaglineView;
