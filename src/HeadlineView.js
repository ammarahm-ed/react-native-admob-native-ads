
import React, { useContext } from "react";
import {
  Text,
  View
} from "react-native";
import { NativeAdContext } from "./context";

const HeadlineView = (props) => {
  const {nativeAd,setNativeAd} = useContext(NativeAdContext);
  
  return (
    <Text
     {...props}
     nativeID="adHeadlineView"
     >
      {nativeAd? nativeAd.headline : null}
    </Text>
  );
}


export default HeadlineView;
