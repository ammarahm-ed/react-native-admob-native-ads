
import React from "react";
import {
  View,
} from "react-native";
import StarRating from 'react-native-star-rating';
const StarRatingView = ({ props }) => {

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
        rating={props.rating}
      />
  </View>);
}

export default StarRatingView;
