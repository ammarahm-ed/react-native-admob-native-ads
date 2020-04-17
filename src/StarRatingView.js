
import React, { useContext, createRef } from "react";
import {
  View, Platform, findNodeHandle,
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";
const starRatingRef = createRef();

const StarRatingView = ( props ) => {
  const {nativeAd, setNativeAd} = useContext(NativeAdContext);

  return (<View
    style={props.style}
    ref={starRatingRef}
    onLayout={() => {
      if (Platform.OS === "android") return;
      let handle = findNodeHandle(starRatingRef.current);
      nativeAdView.current?.setNativeProps({
        starrating: handle
      });
    }}
    nativeID="adStarRating"
  >  
     
  </View>);
}

export default StarRatingView;
