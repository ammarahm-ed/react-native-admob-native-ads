import {NativeModules} from 'react-native';

const RNAdmobNativeAdsManager = NativeModules.RNAdmobNativeAdsManager;

const MAX_AD_CONTENT_RATING = {
  G: "G",
  MA:"MA",
  PG: "PG",
  T: "T",
  UNSPECIFIED: "",
}
const TAG_FOR_CHILD_DIRECTED_TREATMENT = {
  TRUE:1,
  FALSE:0
}

const TAG_FOR_UNDER_AGE_CONSENT = {
  TRUE:1,
  FALSE:0
}

function setRequestConfiguration(config) {

  RNAdmobNativeAdsManager.setRequestConfiguration(config);
}

async function isTestDevice() {

  return await RNAdmobNativeAdsManager.isTestDevice();
}

export default {
  setRequestConfiguration,
  isTestDevice,
  MAX_AD_CONTENT_RATING,
  TAG_FOR_CHILD_DIRECTED_TREATMENT,
  TAG_FOR_UNDER_AGE_CONSENT
}