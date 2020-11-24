import {NativeModules} from 'react-native';

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

function setRequestConfiguration(config) {
  RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

function isTestDevice() {
  return RNAdmobNativeAdsManager.isTestDevice();
}

function setAdUnitId(id) {
  return RNAdmobNativeAdsManager.setAdUnitId(id);
}

function preload() {
  return RNAdmobNativeAdsManager.preload();
}

export default {
  setRequestConfiguration,
  isTestDevice,
  setAdUnitId,
  preload
}
