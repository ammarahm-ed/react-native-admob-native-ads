import {NativeModules} from 'react-native';

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

function setRequestConfiguration(config) {
  RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

function isTestDevice() {
  return RNAdmobNativeAdsManager.isTestDevice();
}

function setAdUnitIds(nativeAdId,nativeVideoAdId) {
  return RNAdmobNativeAdsManager.setAdUnitIds(nativeAdId,nativeVideoAdId);
}

function setNumberOfAdsToLoad(nativeAdsToLoad,nativeVideoAdsToLoad) {
  return RNAdmobNativeAdsManager.setNumberOfAdsToLoad(nativeAdsToLoad,nativeVideoAdsToLoad);
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
  setNumberOfAdsToLoad
}
