import { arrayOf, func, object, string } from "prop-types";
import React, { Component, createRef } from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  ViewPropTypes,
} from "react-native";

const nativeAdView = createRef();

class NativeAdView extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    const { adUnitID } = this.props;
    if (adUnitID) {
      return this.loadNativeAd(adUnitID);
    }
    console.warn("Attempted to load native ad without ad unit id");
  }

  loadNativeAd(adUnitId) {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(nativeAdView),
      UIManager.getViewManagerConfig("RNGADNativeView").Commands.loadNativeAd,
      [adUnitId]
    );
  }

  render() {
    return (
      <UnifiedNativeAdView
        {...this.props}
        ref={nativeAdView}
      />
    );
  }
}

NativeAdView.simulatorId = "SIMULATOR";

NativeAdView.propTypes = {
  ...ViewPropTypes,
  adSize: string,
  adUnitID: string,
  buttonStyle: object,
  backgroundStyle: object,
  headlineTextColor: string,
  descriptionTextColor: string,
  advertiserTextColor: string,
  ratingBarColor: string,
  testDevices: arrayOf(string),
  onAdOpened: func,
  onAdClosed: func,
  onAdLeftApplication: func,
  onAdImpression: func,
  onAdClick: func,
  onAdLoaded: func,
  onAdFailedToLoad: func
};

const UnifiedNativeAdView = requireNativeComponent(
  "RNGADNativeView",
  NativeAdView
);

export default NativeAdView;
