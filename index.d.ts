import { ViewStyle,TextProps,ImageProps, ViewProps, TextStyle} from "react-native";
import {StarRatingProps} from "react-native-star-rating";


type NativeAd = {

  /**
   * Title of the native ad.
   */
  headline:string;

  /**
   * Description of the native ad.
   */
  tagline:string;
  /**
   * Advertiser of the native ad.
   */
  advertiser:string;
  /**
   * If the ad is for a paid app or service, price value.
   */
  price:string;
  /**
   * If ad is for an app, then name of the store.
   */
  store:string;
  /**
   * Icon / Logo of the adveriser or the product advertised in ad.
   */
  icon:string;
  /**
   * An array of images along with the ad.
   */
  images:Array<string>;
  /**
   * callToAction text value for the native ad.
   */
  callToAction:string;
  /**
   * If ad is for an app, then its rating on the respective store.
   */
  rating:number;
}

type NativeAdViewProps = {
  style?: ViewStyle;

  /**
   * Ad Unit ID for Native ads. Remember to use only test-ids during
   * development mode or add your device to testDevices.
   */

  adUnitID: string;

  /**
   * Time after which a new ad should be
   * requested from the server. Default is 1 minute (60000 ms);
   */
  refreshInterval?: number;

  /**
   * Set testdevices for the ad. 
   */
  testDevices?: Array<string>;
  onAdOpened?: Function<void>;
  onAdClosed?: Function<void>;
  onAdLeftApplication?: Function<void>;
  onAdImpression?: Function<void>;
  onAdClick?: Function<void>;
  onAdLoaded?: Function<void>;
  onUnifiedNativeAdLoaded?:(event:NativeAd) => {};
  onAdFailedToLoad?: Function<void>;
};

type SimpleViewProps = {
  style?: ViewStyle;

};

type NestedTextProps = {
  style:ViewStyle;
  textStyle:TextStyle;
}

declare module "react-native-admob-native-ads" {

  export default function NativeAdView(props: NativeAdViewProps): React.FunctionComponent;
  export function HeaderView(props:NestedTextProps): React.FunctionComponent;
  export function HeadlineView(props: TextProps): React.FunctionComponent;
  export function TaglineView(props: TextProps): React.FunctionComponent;
  export function AdvertiserView(props: TextProps): React.FunctionComponent;
  export function PriceView(props: TextProps): React.FunctionComponent;
  export function StoreView(props: TextProps): React.FunctionComponent;
  export function ImageView(props: ImageProps): React.FunctionComponent;
  export function IconView(props: ImageProps): React.FunctionComponent;
  export function MediaView(props: SimpleViewProps): React.FunctionComponent;
  export function CallToActionView(props: NestedTextProps): React.FunctionComponent;
  export function StarRatingView(props: StarRatingProps): React.FunctionComponent;
}
