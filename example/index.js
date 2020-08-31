/**
 * @format
 */
import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import { AdManager } from 'react-native-admob-native-ads';


AdManager.setRequestConfiguration({
  tagForChildDirectedTreatment: false
})

AppRegistry.registerComponent(appName, () => App);
