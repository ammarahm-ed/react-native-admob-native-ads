import React, { useContext, createRef, useEffect } from "react";
import { findNodeHandle } from "react-native";
import { NativeAdContext } from "./context";
import StarRating from "react-native-star-rating";

const StarRatingView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const starRatingRef = createRef();
  const _onLayout = () => {
    if (!nativeAdView) return;
    let handle = findNodeHandle(starRatingRef.current);
    nativeAdView.setNativeProps({
      starrating: handle,
    });
  };
  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  return nativeAd && nativeAd.rating && nativeAd.rating > 0 ? (
    <StarRating
      {...props}
      ref={starRatingRef}
      maxStars={5}
      rating={nativeAd.rating ? nativeAd.rating : 0}
      onLayout={_onLayout}
    />
  ) : null;
};

export default StarRatingView;
