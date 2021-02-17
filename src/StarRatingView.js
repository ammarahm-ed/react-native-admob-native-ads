import React, { useCallback, useContext, useEffect, useRef } from "react";
import { findNodeHandle } from "react-native";
import { NativeAdContext } from "./context";
import StarView from "./StarView";

const StarRatingView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const starRatingRef = useRef();
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;

    let handle = findNodeHandle(starRatingRef.current);
    nativeAdView.setNativeProps({
      starrating: handle,
    });
  }, [nativeAdView, starRatingRef]);

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
