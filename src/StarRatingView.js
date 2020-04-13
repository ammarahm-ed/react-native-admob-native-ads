
import React, { useContext } from "react";
import {
  View,
} from "react-native";
import StarRating from 'react-native-star-rating';
import { NativeAdContext } from "./context";

const StarRatingView = ( props ) => {
  const {nativeAd, setNativeAd} = useContext(NativeAdContext);

  return (<View
    style={props.style}
    nativeID="adStarRating"
  >  
      <StarRating
        maxStars={5}
        emptyStarColor={props.emptyStarColor}
        fullStarColor={props.fullStarColor}
        halfStarColor={props.halfStarColor}
        starSize={props.starSize}
        rating={nativeAd? nativeAd.rating : null}
      />
  </View>);
}

export default StarRatingView;
