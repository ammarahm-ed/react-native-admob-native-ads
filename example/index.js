/**
 * @format
 */
import {AppRegistry, Platform} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import {AdManager} from 'react-native-admob-native-ads';

AdManager.setRequestConfiguration({
  tagForChildDirectedTreatment: false,
});

const NATIVE_AD_ID =
  Platform.OS === 'ios'
    ? 'ca-app-pub-3940256099942544/3986624511'
    : 'ca-app-pub-3940256099942544/2247696110';

const NATIVE_AD_VIDEO_ID =
  Platform.OS === 'ios'
    ? 'ca-app-pub-3940256099942544/2521693316'
    : 'ca-app-pub-3940256099942544/1044960115';

// image test ad
AdManager.loadNativeAds({
  adUnitId: NATIVE_AD_ID,
  numOfAds: 3,
  requestNonPersonalizedAdsOnly: false,
});

// video test ad
AdManager.loadNativeAds({
  adUnitId: NATIVE_AD_VIDEO_ID,
  numOfAds: 3,
  requestNonPersonalizedAdsOnly: false,
});

// setTimeout(() => {
//   AdManager.hasLoadedAd(NATIVE_AD_ID).then((value) =>
//     console.log('image ad: ', value),
//   );
//   AdManager.hasLoadedAd(NATIVE_AD_VIDEO_ID).then((value) =>
//     console.log('video ad: ', value),
//   );
//   AdManager.printAds();
// }, 10000);

AppRegistry.registerComponent(appName, () => App);
