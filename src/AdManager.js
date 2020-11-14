import {NativeModules} from 'react-native';

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

function setRequestConfiguration(config) {
  RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

function isTestDevice() {
  return RNAdmobNativeAdsManager.isTestDevice();
}

function registerNativeAd(config) {
  return RNAdmobNativeAdsManager.registerNativeAd(config);
}

function unRegisterNativeAd(id) {
  return RNAdmobNativeAdsManager.unRegisterNativeAd(id);
}

async function hasLoadedAd(id) {
  return RNAdmobNativeAdsManager.hasLoadedAd(id);
}

async function resetCache() {
  return RNAdmobNativeAdsManager.resetCache();
}

export default {
  setRequestConfiguration,
  isTestDevice,
  registerNativeAd,
  hasLoadedAd,
  unRegisterNativeAd,
  resetCache,
}
