
import React, { useContext } from "react";
import {
  View,
} from "react-native";
import { NativeAdContext } from "./context";

const StarRatingView = ( props ) => {
  const {nativeAd, setNativeAd} = useContext(NativeAdContext);

  return (<View
    style={props.style}
    nativeID="adStarRating"
  >  
     
  </View>);
}

export default StarRatingView;
