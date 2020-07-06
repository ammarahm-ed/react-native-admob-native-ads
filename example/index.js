/**
 * @format
 */

import {AppRegistry,NativeModules} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import { AdManager } from 'react-native-admob-native-ads';


AdManager.setRequestConfiguration({
  tagForChildDirectedTreatment:AdManager.TAG_FOR_CHILD_DIRECTED_TREATMENT.FALSE
})

AppRegistry.registerComponent(appName, () => App);
