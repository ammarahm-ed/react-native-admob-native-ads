import React, { useState, useEffect } from "react";
import {
  requireNativeComponent,
  View
} from "react-native";
import { NativeAdContext, nativeAdView } from "./context";
import Wrapper from "./Wrapper"
const NativeAdView = (props) => {
  const [nativeAd, setNativeAd] = useState(null);
  const [forceRefresh, setForceRefresh] = useState(false);
  function updateAd(ad) {
    if (ad) {
      setNativeAd(ad)
    }
  }

  const _onAdFailedToLoad = (event) => {

    if (props.onAdFailedToLoad) {
      props.onAdFailedToLoad(event);
    }
  }

  const _onAdLoaded = (event) => {

    if (props.onAdLoaded)
      props.onAdLoaded(event.nativeEvent);
  }

  const _onAdClicked = (event) => {
    if (props.onAdClicked)
      props.onAdClicked(event.nativeEvent);
  }

  const _onAdImpression = (event) => {
    if (props.onAdImpression)
      props.onAdImpression(event.nativeEvent);
  }

  const _onAdClosed = (event) => {
    if (props.onAdClosed)
      props.onAdClosed(event.nativeEvent);
  }

  const _onAdOpened = (event) => {
    if (props.onAdOpened)
      props.onAdOpened(event.nativeEvent);
     
  }

  const _onUnifiedNativeAdLoaded = event => {
 
    updateAd(event.nativeEvent)
    setTimeout(() => {
      setForceRefresh(!forceRefresh);
      setForceRefresh(!forceRefresh);
      setForceRefresh(!forceRefresh)
    }, 0)
    if (props.onUnifiedNativeAdLoaded) {
      props.onUnifiedNativeAdLoaded(event.nativeEvent);
    }
  }

  const _onAdLefApplication = (event) => {

    if (props.onAdLeftApplication)
      props.onAdLeftApplication(event.nativeEvent);

  }

  return (
    <NativeAdContext.Provider
      value={{ nativeAd, setNativeAd }}
    >
      <UnifiedNativeAdView
        ref={nativeAdView}
        onAdLoaded={_onAdLoaded}
        onAdFailedToLoad={_onAdFailedToLoad}
        onAdClicked={_onAdClicked}
        onAdLeftApplication={_onAdLefApplication}
        onAdOpened={_onAdOpened}
        onAdClosed={_onAdClosed}
        onAdImpression={_onAdImpression}
        style={props.style}
        onUnifiedNativeAdLoaded={_onUnifiedNativeAdLoaded}
        adUnitID="ca-app-pub-3940256099942544/2247696110"
      >
         <Wrapper
            style={{
              height: '100%',
              width: '100%',
            }}
          > 
        
          {props.children}
          </Wrapper>
      </UnifiedNativeAdView>

    </NativeAdContext.Provider>
  );

}

NativeAdView.simulatorId = "SIMULATOR";


const UnifiedNativeAdView = requireNativeComponent(
  "RNGADNativeView",
  NativeAdView
);

export default NativeAdView;
