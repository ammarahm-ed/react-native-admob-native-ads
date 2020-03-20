import { arrayOf, func, object, string } from "prop-types";
import React, { Component } from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  UIManager,
  ViewPropTypes,
  Platform
} from "react-native";

class NativeAdView extends Component {
  constructor() {
    super();
    this.handleAdFailedToLoad = this.handleAdFailedToLoad.bind(this);
    this.state = {
      style: {
        
      }
    };
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
      findNodeHandle(this._nativeView),
      UIManager.getViewManagerConfig("RNGADNativeView").Commands.loadNativeAd,
      [adUnitId]
    );
  }

  getHeight() {
   
      if (this.props.adSize === "small") {
        return 120
      } else if (this.props.adSize === "medium") {
        return 160;
      } else {
        return 410
      }
   
  }

  handleAdFailedToLoad(event) {
    if (this.props.onAdFailedToLoad) {
      this.props.onAdFailedToLoad(event.nativeEvent.error);
    }
  }

  setRef = el => (this._nativeView = el);

  render() {
    return (
      <UnifiedNativeAdView
        {...this.props}
        style={[this.props.style, this.state.style, {
          height: Platform.OS === "android"? this.getHeight() : null
        }]}
        onAdFailedToLoad={this.handleAdFailedToLoad}
        ref={this.setRef}
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
