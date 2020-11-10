/**
 * @format
 */
import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import {AdManager} from 'react-native-admob-native-ads';

AdManager.setRequestConfiguration({
  tagForChildDirectedTreatment: false,
});

AdManager.loadNativeAds({
  adUnitId: 'ca-app-pub-3940256099942544/2247696110',
  numOfAds: 3,
  requestNonPersonalizedAdsOnly: false,
});

setTimeout(() => {
  AdManager.hasLoadedAd(
    'ca-app-pub-3940256099942544/2247696110',
  ).then((value) => console.log('tttttt', value));
  AdManager.printAds();
}, 10000);

AppRegistry.registerComponent(appName, () => App);
