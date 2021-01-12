import {DeviceEventEmitter, NativeModules} from 'react-native';

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

function setRequestConfiguration(config) {
  RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

function isTestDevice() {
  return RNAdmobNativeAdsManager.isTestDevice();
}

function setAdUnitIds(adUnitId, videoAdUnitId) {
  return RNAdmobNativeAdsManager.setAdUnitIds(adUnitId, videoAdUnitId);
}

function setNumberOfAdsToLoad(imageAdsToLoad, videoAdsToLoad) {
  return RNAdmobNativeAdsManager.setNumberOfAdsToLoad(
    imageAdsToLoad,
    videoAdsToLoad,
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

function addListener(type,listener) {
  DeviceEventEmitter.addListener(type,listener)
}

function removeListener(type,listener) {
  DeviceEventEmitter.removeListener(type,listener)
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
  removeListener,
  addListener
};
