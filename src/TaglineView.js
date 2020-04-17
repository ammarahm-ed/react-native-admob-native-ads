
import React, { useContext, createRef } from "react";
import {
  Text, Platform, findNodeHandle,
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";

const taglineRef = createRef();
const TaglineView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  return (
    <Text
      {...props}
      ref={taglineRef}
      onLayout={() => {
        if (Platform.OS === "android") return;
        let handle = findNodeHandle(taglineRef.current);
        nativeAdView.current?.setNativeProps({
          tagline: handle
        });
      }}
      nativeID="adTaglineView"
    >
      {nativeAd ? nativeAd.tagline : null}
    </Text>
  );
}

export default TaglineView;
