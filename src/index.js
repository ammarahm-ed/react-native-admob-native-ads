import React, { useEffect, useState } from "react";
import { Platform, requireNativeComponent } from "react-native";
import { NativeAdContext, nativeAdView } from "./context";
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
  images: ["https://dummyimage.com/qvga"],
};

const NativeAdView = (props) => {
  const [nativeAd, setNativeAd] = useState(null);
  const [forceRefresh, setForceRefresh] = useState(false);
  function updateAd(ad) {
    if (ad) {
      setNativeAd(ad);
    }
  }

  const _onAdFailedToLoad = (event) => {
    if (props.onAdFailedToLoad) {
      props.onAdFailedToLoad(event);
    }
  };

  const _onAdLoaded = (event) => {
    if (props.onAdLoaded) props.onAdLoaded(event.nativeEvent);
  };

  const _onAdClicked = (event) => {
    if (props.onAdClicked) props.onAdClicked(event.nativeEvent);
  };

  const _onAdImpression = (event) => {
    if (props.onAdImpression) props.onAdImpression(event.nativeEvent);
  };

  const _onAdClosed = (event) => {
    if (props.onAdClosed) props.onAdClosed(event.nativeEvent);
  };

  const _onAdOpened = (event) => {
    if (props.onAdOpened) props.onAdOpened(event.nativeEvent);
  };

  const _onUnifiedNativeAdLoaded = (event) => {
    updateAd(event.nativeEvent);
    setTimeout(() => {
      setForceRefresh(!forceRefresh);
      setForceRefresh(!forceRefresh);
      setForceRefresh(!forceRefresh);
    }, 0);
    if (props.onUnifiedNativeAdLoaded) {
      props.onUnifiedNativeAdLoaded(event.nativeEvent);
    }
  };

  const _onAdLefApplication = (event) => {
    if (props.onAdLeftApplication) props.onAdLeftApplication(event.nativeEvent);
  };

  useEffect(() => {
    if (props.enableTestMode) {
      updateAd(testNativeAd);
    } else {
      updateAd(null);
    }
  }, [props.enableTestMode]);

  return (
    <NativeAdContext.Provider value={{ nativeAd, setNativeAd }}>
      <UnifiedNativeAdView
        ref={nativeAdView}
        onAdLoaded={_onAdLoaded}
        onAdFailedToLoad={_onAdFailedToLoad}
        onAdClicked={_onAdClicked}
        onAdLeftApplication={_onAdLefApplication}
        onAdOpened={_onAdOpened}
        onAdClosed={_onAdClosed}
        onAdImpression={_onAdImpression}
        delayAdLoad={props.delayAdLoad? props.delayAdLoad : 1000}
        style={props.style}
        onUnifiedNativeAdLoaded={_onUnifiedNativeAdLoaded}
        adUnitID={props.adUnitID}
      >
        <Wrapper
          style={{
            height: "100%",
            width: "100%",
          }}
        >
          {props.children}
        </Wrapper>
      </UnifiedNativeAdView>
    </NativeAdContext.Provider>
  );
};

NativeAdView.simulatorId = "SIMULATOR";

const UnifiedNativeAdView = requireNativeComponent(
  "RNGADNativeView",
  NativeAdView
);

export default NativeAdView;
