import React, { useContext, createRef } from "react";
import { Text, Platform, findNodeHandle } from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const taglineRef = createRef();
const TaglineView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  const _onLayout = () => {
    let handle = findNodeHandle(taglineRef.current);
    nativeAdView.current?.setNativeProps({
      tagline: handle,
    });
  };

  return (
    <Text {...props} ref={taglineRef} onLayout={_onLayout}>
      {nativeAd ? nativeAd.tagline : null}
    </Text>
  );
};

export default TaglineView;
