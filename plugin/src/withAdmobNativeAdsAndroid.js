const {
  AndroidConfig,
  withAndroidManifest,
} = require('@expo/config-plugins');

const { addMetaDataItemToMainApplication, getMainApplicationOrThrow } = AndroidConfig.Manifest;

function withAdmobNativeAdsManifest(config, props) {
  return withAndroidManifest(config, (config) => {
    config.modResults = setAdmobNativeAdsConfig(config.modResults, props);
    return config;
  });
};

function setAdmobNativeAdsConfig(androidManifest, props) {
  let mainApplication = getMainApplicationOrThrow(androidManifest);
  addMetaDataItemToMainApplication(
      mainApplication,
      'com.google.android.gms.ads.APPLICATION_ID',
      props.androidAppId
  );

  return androidManifest;
}

function withAdmobNativeAdsAndroid(config, props) {
  return withAdmobNativeAdsManifest(config, props);
};

module.exports = withAdmobNativeAdsAndroid;