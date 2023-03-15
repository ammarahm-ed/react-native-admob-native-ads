import {ConfigPlugin, createRunOncePlugin} from '@expo/config-plugins';
import { withAdmobNativeAdsAndroid } from './withAdmobNativeAdsAndroid';
import { withAdmobNativeAdsGradle } from './withAdmobNativeAdsAppBuildGradle';
import { withAdmobNativeAdsPlist } from './withAdmobNativeAdsInfoPlist';
import { withAdmobNativeAdsPodNat } from './withAdmobNativeAdsPod';

const pkg = require('react-native-admob-native-ads/package.json');

const withAdmobNativeAds: ConfigPlugin = (config, props) => {
    config = withAdmobNativeAdsAndroid(config, props);
    config = withAdmobNativeAdsGradle(config);
    config = withAdmobNativeAdsPlist(config, props);
    config = withAdmobNativeAdsPodNat(config);
    return config;
};

export default createRunOncePlugin(withAdmobNativeAds, pkg.name, pkg.version);