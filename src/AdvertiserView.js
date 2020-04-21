import React, { useContext, createRef } from "react";
import { Text, Platform, findNodeHandle } from "react-native";
import { NativeAdContext, nativeAdView } from "./context";
const advertiserRef = createRef();

const AdvertiserView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  const _onLayout = () => {
 
    let handle = findNodeHandle(advertiserRef.current);
    nativeAdView.current?.setNativeProps({
      advertiser: handle,
    });
  };

  return (
    <Text {...props} nativeID="adAdvertiserView" onLayout={_onLayout}>
      {nativeAd ? props.allCaps? nativeAd.advertiser?.toUpperCase() : nativeAd.advertiser : null}
    </Text>
  );
};

export default AdvertiserView;
