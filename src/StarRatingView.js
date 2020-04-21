import React, { createRef, useContext } from "react";
import { findNodeHandle } from "react-native";
import StarRating from "react-native-star-rating";
import { NativeAdContext, nativeAdView } from "./context";
const starRatingRef = createRef();

const StarRatingView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);

  const _onLayout = () => {
    let handle = findNodeHandle(starRatingRef.current);
    nativeAdView.current?.setNativeProps({
      starrating: handle,
    });
  };

  return nativeAd && nativeAd.rating && nativeAd.rating > 0.5 ? (
    <StarRating
      {...props}
      ref={starRatingRef}
      rating={nativeAd ? nativeAd.rating : null}
      onLayout={_onLayout}
    />
  ) : null;
};

export default StarRatingView;
