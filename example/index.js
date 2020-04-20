/**
 * @format
 */

import {AppRegistry,NativeModules} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
const AdsManger = NativeModules.RNAdmobNativeAdsManager;

AdsManger.loadAds("ca-app-pub-3940256099942544/2247696110",5,1000000);

AppRegistry.registerComponent(appName, () => App);
