import {NativeModules} from 'react-native';
import { AdOptions } from './utils';

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

async function setRequestConfiguration(config) {
  return RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

async function isTestDevice() {
  return RNAdmobNativeAdsManager.isTestDevice();
}

function registerRepository(config) {
  config.mediaAspectRatio = AdOptions.mediaAspectRatio[config.mediaAspectRatio || "unknown"];
  config.adChoicesPlacement = AdOptions.adChoicesPlacement[config.adChoicesPlacement || "topRight"];
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
