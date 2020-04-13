
import React, { useContext } from "react";
import {
  Text,
  View
} from "react-native";

const AdvertiserView = ( props ) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);
  return (<View
    style={props.style}
    nativeID="adAdvertiserText"
  >
    <Text
      textStyle={props.textStyle}
    >
      {nativeAd ? nativeAd.advertiser : null}
    </Text>
  </View>);
}


export default AdvertiserView;
