import React, { Component } from "react";
import {
  findNodeHandle,
  Platform,
  requireNativeComponent,
  UIManager
} from "react-native";
import BatchedBridge from "react-native/Libraries/BatchedBridge/BatchedBridge";
import { defaultAd, NativeAdContext } from "./context";
import { AdOptions } from "./utils";
import Wrapper from "./Wrapper";

const testNativeAd = {
  headline: "Test Ad: Lorem ipsum dolor ",
  tagline:
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod",
  advertiser: "Laboris Nisi",
  store: Platform.OS === "ios" ? "AppStore" : "Google Play",
  video: false,
  rating: 4.5,
  price: "$1",
  icon: "https://dummyimage.com/300.png/09f/fff",
  images: [{ url: "https://dummyimage.com/qvga" }],
};

export class NativeAdView extends Component {
  constructor(props) {
    super(props);
    this.state = {
      nativeAd: defaultAd,
      nativeAdView: null,
    };
    this.nativeAdRef;
    this.currentId = 0;
    this.delayDuration = 0;
    this.componentMounted = false;
    this.ad = defaultAd;
  }

  messagingModuleName = `NativeAdMessageHandler${Date.now() + Math.random()}`;

  _onAdFailedToLoad = (event) => {
    if (this.props.onAdFailedToLoad) {
      this.props.onAdFailedToLoad(event.nativeEvent);
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
    try {
      this.componentMounted = true;
      if (this.props.enableTestMode) {
        this.updateAd(testNativeAd);
      } else {
        this.updateAd(this.ad);
      }
      BatchedBridge.registerCallableModule(this.messagingModuleName, this);
    } catch (e) {}
  }

  componentWillUnmount() {
    this.componentMounted = false;
  }

  _getRef = (ref) => {
    this.nativeAdRef = ref;
    return this.nativeAdRef;
  };

  loadAd = () => {
    UIManager.dispatchViewManagerCommand(
      findNodeHandle(this.nativeAdRef),
      UIManager.getViewManagerConfig("RNGADNativeView").Commands.loadAd,
      undefined
    );
  };

  render() {
    const { nativeAd, nativeAdView } = this.state;
    return (
      <NativeAdContext.Provider value={{ nativeAd, nativeAdView }}>
        <UnifiedNativeAdView
          ref={this._getRef}
          adUnitID={this.props.adUnitID}
          onAdLoaded={this._onAdLoaded}
          onAdFailedToLoad={this._onAdFailedToLoad}
          onAdClicked={this._onAdClicked}
          onAdLeftApplication={this._onAdLefApplication}
          onAdOpened={this._onAdOpened}
          onAdClosed={this._onAdClosed}
          onAdImpression={this._onAdImpression}
          style={this.props.style}
          mediaAspectRatio={
            AdOptions.mediaAspectRatio[this.props.mediaAspectRatio]
          }
          onUnifiedNativeAdLoaded={this.onUnifiedNativeAdLoaded}
          messagingModuleName={this.messagingModuleName}
          requestNonPersonalizedAdsOnly={
            this.props.requestNonPersonalizedAdsOnly
          }
          videoOptions={this.props.videoOptions}
          mediationOptions={this.props.mediationOptions}
          targetingOptions={this.props.targetingOptions}
          adChoicesPlacement={AdOptions.adChoicesPlacement[this.props.adChoicesPlacement]}
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
    );
  }
}

NativeAdView.defaultProps = {
  mediaAspectRatio: "any",
  adChoicesPlacement: "topRight",
  requestNonPersonalizedAdsOnly: false,
  videoOptions: {
    muted: false,
    clickToExpand: false,
    clickToExpand: false,
  },
  mediationOptions: {
    nativeBanner: false,
  },
};

NativeAdView.simulatorId = "SIMULATOR";

const UnifiedNativeAdView = requireNativeComponent(
  "RNGADNativeView",
  NativeAdView
);

export default NativeAdView;
