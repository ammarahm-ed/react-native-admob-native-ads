import React, { useContext, useRef, useEffect } from "react";
import { findNodeHandle } from "react-native";
import StarView from "./StarView";
import { NativeAdContext } from "./context";

const StarRatingView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const starRatingRef = useRef();
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
    <StarView
      {...props}
      passRef={starRatingRef}
      stars={nativeAd.rating ? nativeAd.rating : 0}
      onLayout={_onLayout}
    />
  ) : null;
};

export default StarRatingView;
