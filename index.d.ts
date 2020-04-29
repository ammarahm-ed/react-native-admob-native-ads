import { ViewStyle, TextProps, ImageProps, TextStyle } from "react-native";
import { StarRatingProps } from "react-native-star-rating";


type Image = {
  /**
   * The url of the image 
   */
  url:string;
  /**
   * The height of the image
   */
  height:number;
    /**
   * The width of the image
   */
  width:number;
}


type NativeAd = {
  /**
   * Title of the native ad.
   */
  headline: string;

  /**
   * Description of the native ad.
   */
  tagline: string;
  /**
   * Advertiser of the native ad.
   */
  advertiser: string;
  /**
   * If the ad is for a paid app or service, price value.
   */
  price: string;

  /**
   * Aspect ratio of the Content loaded inside MediaView.
   * You should use this to calculate the correct width and height of the MediaView.
   */
  aspectRatio:number;
  /**
   * If ad is for an app, then name of the store.
   */
  store: string;
  /**
   * Icon / Logo of the adveriser or the product advertised in ad.
   */
  icon: string;
  /**
   * An array of images along with the ad.
   */
  images: Array<Image>;
  /**
   * callToAction text value for the native ad.
   */
  callToAction: string;
  /**
   * If ad is for an app, then its rating on the respective store.
   */
  rating: number;

  /**
   * if ad has video content or not.
   */
  video: boolean;
};

type NativeAdViewProps = {
  /**
   * When you are designing your ad, placeholders
   * for the ad will be loaded so you can easily
   * design, because for each type of ad
   * recieved from the server, not all 
   * the info is available.
   */

  enableTestMode?: boolean;

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
   * Time in milliseconds to delay ad rendering. 
   * Use this if you are rendering multiple ads
   * in your screen such as in a list. Default is 0ms.
   * This is usually done so that ad request is done 
   * after the views are attached.
   */

  delayAdLoading?: number;

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
  onUnifiedNativeAdLoaded?: (event: NativeAd) => {};
  onAdFailedToLoad?: Function<void>;
};

type SimpleViewProps = {
  style?: ViewStyle;
};

type NestedTextProps = {
  style?: ViewStyle;
  textStyle?: TextStyle;
  allCaps?: boolean;
};

declare module "react-native-admob-native-ads" {
  /**
   *
   * Wrapper for the UnifiedNativeAdView from Google Ads SDK. All your views should be
   * wrapped inside this view always.
   *
   */

  export default function NativeAdView(
    props: NativeAdViewProps
  ): React.FunctionComponent;

  /**
   * Ad Badge shows the {ad} badge on top of the ad telling the user that this is an AD.
   *
   */

  export function AdBadge(props: NestedTextProps): React.FunctionComponent;

  /**
   * The title of the native ad recieved from server is renderd here.
   * You dont need to pass any values to it. It will automatically get the title from
   * context and load it.
   * You should on style it as you want.
   */
  export function HeadlineView(props: TextProps): React.FunctionComponent;

  /**
   *  * The description of the native ad recieved from server is renderd here.
   */
  export function TaglineView(props: TextProps): React.FunctionComponent;

  /**
   *  * The adveriser name of the native ad recieved from server is renderd here.
   */
  export function AdvertiserView(props: TextProps): React.FunctionComponent;
  /**
   * If the ad or service is paid,then this view can be used to show the price.
   */
  export function PriceView(props: TextProps): React.FunctionComponent;

  /**
   * Many times, the ad recieved will be from the Google Playstore or AppStore for iOS.
   * In that case, you can show the store name using this view.
   */
  export function StoreView(props: TextProps): React.FunctionComponent;

  /**
   * If you want to explicitly show only Images and no video etc, use ImageView.
   */
  export function ImageView(props: ImageProps): React.FunctionComponent;
  /**
   * Icon/Logo of the adveriser shown inside this view
   */
  export function IconView(props: ImageProps): React.FunctionComponent;

  /**
   * If the ad has images or video content. It will be loaded inside the MediaView.
   */
  export function MediaView(props: SimpleViewProps): React.FunctionComponent;

  /**
   * A simple button to open the adveriser website or store page etc. It is a simple
   * Text Component wrapped in a View. I dont know how to make the Touchables or Buttons
   * work since they have no effect. Native side does not recieve the call hence simple
   * Text Component is used to receive the clicks.
   */
  export function CallToActionView(
    props: NestedTextProps
  ): React.FunctionComponent;

  /**
   * A Star Rating View to show the star rating for the app ads that you might recieve from
   * the server.
   */
  export function StarRatingView(
    props: StarRatingProps
  ): React.FunctionComponent;
}
