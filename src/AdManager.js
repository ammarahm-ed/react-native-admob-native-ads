import {NativeModules} from 'react-native';

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

function setRequestConfiguration(config) {
  RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

function isTestDevice() {
  return RNAdmobNativeAdsManager.isTestDevice();
}

function registerRepository(config) {
  return RNAdmobNativeAdsManager.registerRepository(config);
}

function unRegisterRepository(name) {
  return RNAdmobNativeAdsManager.unRegisterRepository(name);
}

async function hasAd(name) {
  return RNAdmobNativeAdsManager.hasAd(name);
}

async function resetCache() {
  return RNAdmobNativeAdsManager.resetCache();
}

export default {
  setRequestConfiguration,
  isTestDevice,
  registerRepository,
  hasAd,
  unRegisterRepository,
  resetCache,
}
