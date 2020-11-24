import {NativeModules} from 'react-native';

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

function setRequestConfiguration(config) {
  RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

function isTestDevice() {
  return RNAdmobNativeAdsManager.isTestDevice();
}

function setAdUnitIds(nativeAdId, nativeVideoAdId) {
  return RNAdmobNativeAdsManager.setAdUnitIds(nativeAdId, nativeVideoAdId);
}

function setNumberOfAdsToLoad(nativeAdsToLoad, nativeVideoAdsToLoad) {
  return RNAdmobNativeAdsManager.setNumberOfAdsToLoad(
    nativeAdsToLoad,
    nativeVideoAdsToLoad,
  );
}

function setRequestNonPersonalizedAdsOnly(requestNonPersonalizedAdsOnly) {
  RNAdmobNativeAdsManager.setRequestNonPersonalizedAdsOnly(
    requestNonPersonalizedAdsOnly,
  );
}

function setMediaAspectRatio(aspectRatio) {
  RNAdmobNativeAdsManager.setMediaAspectRatio(aspectRatio);
}

function setAdChoicesPlacement(placement) {
  RNAdmobNativeAdsManager.setAdChoicesPlacement(placement);
}

function setVideosStartMuted(muted) {
  RNAdmobNativeAdsManager.setVideosStartMuted(muted);
}

function preloadNativeAds() {
  return RNAdmobNativeAdsManager.preloadNativeAds();
}

function preloadNativeVideoAds() {
  return RNAdmobNativeAdsManager.preloadNativeVideoAds();
}

export default {
  setRequestConfiguration,
  isTestDevice,
  setAdUnitIds,
  preloadNativeAds,
  preloadNativeVideoAds,
  setNumberOfAdsToLoad,
  setVideosStartMuted,
  setAdChoicesPlacement,
  setMediaAspectRatio,
  setRequestNonPersonalizedAdsOnly,
};
