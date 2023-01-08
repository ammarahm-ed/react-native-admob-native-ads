const withAdmobNativeAdsAndroid = require('./withAdmobNativeAdsAndroid');
const withAdmobNativeAdsAppBuildGradle = require('./withAdmobNativeAdsAppBuildGradle');
const withAdmobNativeAdsInfoPlist = require('./withAdmobNativeAdsInfoPlist');
const withAdmobNativeAdsPod = require('./withAdmobNativeAdsPod');

function withAdmobNativeAds(config, props) {
    withAdmobNativeAdsAndroid(config, props);
    withAdmobNativeAdsAppBuildGradle(config, props);
    withAdmobNativeAdsInfoPlist(config, props);
    withAdmobNativeAdsPod(config, props);
    return config;
};

exports.default = withAdmobNativeAds;