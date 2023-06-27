import { registerRootComponent } from "expo";
import App from "./App";
import { AdManager, TestIds } from "react-native-admob-native-ads";

AdManager.setRequestConfiguration({
  tagForChildDirectedTreatment: false,
});

// image test ad
AdManager.registerRepository({
  name: "imageAd",
  adUnitId: TestIds.Image,
  numOfAds: 3,
  nonPersonalizedAdsOnly: false,
  videoOptions: {
    mute: false,
  },
  expirationPeriod: 3600000, // in milliseconds (optional)
  mediationEnabled: false,
}).then((result) => {
  console.log("registered: ", result);
});

// unmute video test ad
AdManager.registerRepository({
  name: "videoAd",
  adUnitId: TestIds.Video,
  numOfAds: 3,
  nonPersonalizedAdsOnly: false,
  videoOptions: {
    mute: false,
  },
  expirationPeriod: 3600000, // in milliseconds (optional)
  mediationEnabled: false,
}).then((result) => {
  console.log("registered: ", result);
});

// mute video test ad
AdManager.registerRepository({
  name: "muteVideoAd",
  adUnitId: TestIds.Video,
  numOfAds: 3,
  nonPersonalizedAdsOnly: false,
  videoOptions: {
    mute: false,
  },
  expirationPeriod: 3600000, // in milliseconds (optional)
  mediationEnabled: false,
}).then((result) => {
  console.log("registered: ", result);
});

// registerRootComponent calls AppRegistry.registerComponent('main', () => App);
// It also ensures that whether you load the app in Expo Go or in a native build,
// the environment is set up appropriately
registerRootComponent(App);
