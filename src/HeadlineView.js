
import React, { useContext } from "react";
import {
  Text,
  View
} from "react-native";
import { NativeAdContext } from "./context";

const HeadlineView = (props) => {
  const {nativeAd,setNativeAd} = useContext(NativeAdContext);
  
  return (<View
    style={props.style}
    nativeID="adHeadlineView"
  >
    <Text
      textStyle={props.textStyle}
    >
      {nativeAd? nativeAd.headline : null}
    </Text>
  </View>);
}


export default HeadlineView;
