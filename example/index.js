import {AppRegistry, Platform} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import { AdManager } from 'react-native-admob-native-ads';

const NATIVE_AD_ID =
  Platform.OS === 'ios'
    ? 'ca-app-pub-3940256099942544/3986624511'
    : 'ca-app-pub-3940256099942544/2247696110';

const NATIVE_AD_VIDEO_ID =
  Platform.OS === 'ios'
    ? 'ca-app-pub-3940256099942544/2521693316'
    : 'ca-app-pub-3940256099942544/1044960115';

AdManager.setRequestConfiguration({
  tagForChildDirectedTreatment: false
});

AdManager.setNumberOfAdsToLoad(0,2);
AdManager.setAdUnitIds(NATIVE_AD_ID,NATIVE_AD_VIDEO_ID);
AdManager.preloadNativeVideoAds();

AppRegistry.registerComponent(appName, () => App);
