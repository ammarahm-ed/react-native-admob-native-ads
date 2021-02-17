import React, { useCallback, useContext, useRef } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const TaglineView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const taglineRef = useRef();
  let nativeTag = null;
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;
    if (nativeAdView._nativeTag === nativeTag) {
      return;
    }
    nativeTag = nativeAdView._nativeTag
    let handle = findNodeHandle(taglineRef.current);
    nativeAdView.setNativeProps({
      tagline: handle,
    });
  },[nativeAdView,taglineRef])


  return (
    <Text {...props} ref={taglineRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.tagline : null}
    </Text>
  );
};

export default TaglineView;
