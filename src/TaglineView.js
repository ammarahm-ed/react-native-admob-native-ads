import React, { createRef, useContext, useEffect } from "react";
import { findNodeHandle, Text } from "react-native";
import { NativeAdContext } from "./context";

const TaglineView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const taglineRef = createRef();
  const _onLayout = () => {
    if (!nativeAdView) return;
    let handle = findNodeHandle(taglineRef.current);
    nativeAdView.setNativeProps({
      tagline: handle,
    });
  };

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
