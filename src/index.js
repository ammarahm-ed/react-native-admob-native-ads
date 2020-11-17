import React, { Component } from "react";
import { Platform, requireNativeComponent } from "react-native";
import BatchedBridge from "react-native/Libraries/BatchedBridge/BatchedBridge";
import { NativeAdContext } from "./context";
import Wrapper from "./Wrapper";

const testNativeAd = {
  headline: "Test Ad: Lorem ipsum dolor ",
  tagline:
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod",
  advertiser: "Laboris Nisi",
  store: Platform.OS === "ios" ? "AppStore" : "Google Play",
  video: false,
  rating: 4.5,
  price: "$ 1",
  icon: "https://dummyimage.com/300.png/09f/fff",
  images: [{ url: "https://dummyimage.com/qvga" }],
};

export class NativeAdView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      nativeAd: null,
      nativeAdView: null,
    };
    this.nativeAdRef;
    this.currentId = 0;
    this.delayDuration = 0;
    this.componentMounted = false;
    this.ad = null;
  }

  messagingModuleName = `NativeAdMessageHandler${Date.now() + Math.random()}`;

  _onAdFailedToLoad = (event) => {
    if (this.props.onAdFailedToLoad) {
      this.props.onAdFailedToLoad(event);
    }
  };

  _onAdLoaded = (event) => {
    if (this.props.onAdLoaded) this.props.onAdLoaded(event.nativeEvent);
  };

  _onAdClicked = (event) => {
    if (this.props.onAdClicked) this.props.onAdClicked(event.nativeEvent);
  };

  _onAdImpression = (event) => {
    if (this.props.onAdImpression) this.props.onAdImpression(event.nativeEvent);
  };

  _onAdClosed = (event) => {
    if (this.props.onAdClosed) this.props.onAdClosed(event.nativeEvent);
  };

  _onAdOpened = (event) => {
    if (this.props.onAdOpened) this.props.onAdOpened(event.nativeEvent);
  };

  onUnifiedNativeAdLoaded = (event) => {
    this.ad = event.nativeEvent;
    if (this.ad.aspectRatio) {
      this.ad.aspectRatio = parseFloat(this.ad.aspectRatio);  
    }
    if (this.componentMounted) {
      this.updateAd();
      if (this.props.onUnifiedNativeAdLoaded) {
        this.props.onUnifiedNativeAdLoaded(this.ad);
      }
    }
  };

  _onAdLefApplication = (event) => {
    if (this.props.onAdLeftApplication)
      this.props.onAdLeftApplication(event.nativeEvent);
  };

  updateAd() {
    if (this.componentMounted) {
      this.setState({
        nativeAd: this.ad,
      });
    }
  }

  componentDidMount() {
    this.componentMounted = true;
    if (this.props.enableTestMode) {
      this.updateAd(testNativeAd);
    } else {
      this.updateAd(this.ad);
    }
    BatchedBridge.registerCallableModule(this.messagingModuleName, this);
  }

  componentWillUnmount() {
    this.componentMounted = false;
  }

  render() {
    const { nativeAd, nativeAdView, delayRender } = this.state;

    return <NativeAdContext.Provider value={{ nativeAd, nativeAdView }}>
        <UnifiedNativeAdView
          ref={(ref) => {
            this.nativeAdRef = ref;
            return this.nativeAdRef;
          }}
          adUnitID={this.props.adUnitID}
          onAdLoaded={this._onAdLoaded}
          onAdFailedToLoad={this._onAdFailedToLoad}
          onAdClicked={this._onAdClicked}
          onAdLeftApplication={this._onAdLefApplication}
          onAdOpened={this._onAdOpened}
          onAdClosed={this._onAdClosed}
          onAdImpression={this._onAdImpression}
          style={[
            this.props.style,
            Platform.OS === "ios"
              ? { display: this.state.nativeAd ? "flex" : "none" }
              : { height: this.state.nativeAd ? null : 0 },
          ]}
          onUnifiedNativeAdLoaded={this.onUnifiedNativeAdLoaded}
          refreshInterval={
            this.props.refreshInterval ? this.props.refreshInterval : 60000
          }
          messagingModuleName={this.messagingModuleName}
          requestNonPersonalizedAdsOnly={
            this.props.requestNonPersonalizedAdsOnly ? true : false
          }
          adChoicesPlacement={
            this.props.adChoicesPlacement > -1
              ? this.props.adChoicesPlacement
              : 1
          }
        >
          <Wrapper
            onLayout={(event) => {
              this.setState({
                nativeAdView: this.nativeAdRef,
              });
            }}
          >
            {this.props.children}
          </Wrapper>
        </UnifiedNativeAdView>
      </NativeAdContext.Provider>
  }
}

NativeAdView.simulatorId = "SIMULATOR";

const UnifiedNativeAdView = requireNativeComponent(
  "RNGADNativeView",
  NativeAdView
);

export default NativeAdView;
