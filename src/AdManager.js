import {NativeModules} from 'react-native';

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

async function setRequestConfiguration(config) {
  return RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

async function isTestDevice() {
  return RNAdmobNativeAdsManager.isTestDevice();
}

function loadNativeAds(config) {
  return RNAdmobNativeAdsManager.loadNativeAds(config);
}

function printAds() {
  return RNAdmobNativeAdsManager.printAds();
}

async function hasLoadedAd(id) {
  return RNAdmobNativeAdsManager.hasLoadedAd(id);
}

export default {
  setRequestConfiguration,
  isTestDevice,
  loadNativeAds,
  printAds,
  hasLoadedAd,
}
