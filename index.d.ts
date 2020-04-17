import { ViewStyle,TextProps,ImageProps, ViewProps, TextStyle} from "react-native";

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
  onUnifiedNativeAdLoaded?: Function<object>;
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

  export default function NativeAdView(props: SimpleViewProps): React.FunctionComponent;
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

}
