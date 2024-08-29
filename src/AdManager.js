import { DeviceEventEmitter, NativeModules } from "react-native";
import { AdOptions } from "./utils";

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

async function setRequestConfiguration(config) {
  return RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

async function openAdInspector() {
  return RNAdmobNativeAdsManager.openAdInspector();
}

async function openDebugMenu(adUnitId) {
  if (!adUnitId) {
    throw new Error("adUnitId is required to open debug menu");
  }
  return RNAdmobNativeAdsManager.openDebugMenu(adUnitId);
}

async function isTestDevice() {
  return RNAdmobNativeAdsManager.isTestDevice();
}

function registerRepository(config) {
  if (config.mediaAspectRatio) {
    config.mediaAspectRatio =
      AdOptions.mediaAspectRatio[config.mediaAspectRatio];
  }
  if (config.adChoicesPlacement) {
    config.adChoicesPlacement =
      AdOptions.adChoicesPlacement[config.adChoicesPlacement];
  }

  if (config.swipeGestureDirection) {
    config.swipeGestureDirection =
      AdOptions.swipeGestureDirection[config.swipeGestureDirection];
  }

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

function subscribe(repo, eventName, listener) {
  return DeviceEventEmitter.addListener(`${eventName}:${repo}`, listener);
}

export default {
  setRequestConfiguration,
  isTestDevice,
  registerRepository,
  hasAd,
  unRegisterRepository,
  resetCache,
  subscribe,
  openAdInspector,
  openDebugMenu,
};
