/**
 * @format
 */
import {AppRegistry} from 'react-native';
import {AdManager, TestIds} from 'react-native-admob-native-ads';
import App from './App';
import {name as appName} from './app.json';

AdManager.setRequestConfiguration({
  tagForChildDirectedTreatment: false,
});

// image test ad
AdManager.registerRepository({
  name: 'imageAd',
  adUnitId: TestIds.Image,
  numOfAds: 3,
  nonPersonalizedAdsOnly: false,
  videoOptions: {
    mute: false,
  },
  expirationPeriod: 3600000, // in milliseconds (optional)
  mediationEnabled: false,
}).then(result => {
  console.log('registered: ', result);
});

// unmute video test ad
AdManager.registerRepository({
  name: 'videoAd',
  adUnitId: TestIds.Video,
  numOfAds: 3,
  nonPersonalizedAdsOnly: false,
  videoOptions: {
    mute: false,
  },
  expirationPeriod: 3600000, // in milliseconds (optional)
  mediationEnabled: false,
}).then(result => {
  console.log('registered: ', result);
});

// mute video test ad
AdManager.registerRepository({
  name: 'muteVideoAd',
  adUnitId: TestIds.Video,
  numOfAds: 3,
  nonPersonalizedAdsOnly: false,
  videoOptions: {
    mute: false,
  },
  expirationPeriod: 3600000, // in milliseconds (optional)
  mediationEnabled: false,
}).then(result => {
  console.log('registered: ', result);
});

AppRegistry.registerComponent(appName, () => App);
