import IconView from './src/IconView';
import CallToActionView from './src/CallToActionView'
import HeadlineView from './src/HeadlineView'
import TaglineView from './src/TaglineView'
import AdvertiserView from './src/AdvertiserView'
import ImageView from './src/ImageView'
import NativeMediaView from './src/NativeMediaView'
import StoreView from './src/StoreView'
import StarRatingView from './src/StarRatingView'
import PriceView from "./src/PriceView";
import AdBadge from "./src/AdBadge";
import NativeAdView from './src';
import {AdOptions} from "./src/utils"
import AdManager from "./src/AdManager"
import { Platform } from 'react-native';
export default NativeAdView;

const TestIds = Platform.select({
  ios:{
    Video:'ca-app-pub-3940256099942544/2521693316',
    Image:'ca-app-pub-3940256099942544/3986624511'
  },
  android: {
    Video:'ca-app-pub-3940256099942544/1044960115',
    Image:'ca-app-pub-3940256099942544/2247696110'
  }
})

export {
  IconView,
  CallToActionView,
  HeadlineView,
  TaglineView,
  AdvertiserView,
  ImageView,
  NativeMediaView,
  StoreView,
  StarRatingView,
  PriceView,
  AdBadge,
  AdOptions,
  AdManager,
  TestIds
}



