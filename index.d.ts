import { Component } from "react";
import { ViewStyle } from "react-native";

type ButtonStyleType = {
  [name: string]: {
      backgroundColor: string,
      textColor: string,
      borderColor: string,
      borderWidth: number,
      borderRadius: number
  }
};

type BackgroundStyleType = {
  [name: string]: {
    backgroundColor: string,
    borderColor: string,
    borderWidth: number,
    borderRadius: number
  }
};

declare module "react-native-admob-native-ads" {
  export type NativeAdViewProps = {
    style?:ViewStyle,
    adSize?: string,
    adUnitID: string,
    buttonStyle?: ButtonStyleType,
    backgroundStyle?:BackgroundStyleType,
    headlineTextColor?:string,
    descriptionTextColor?:string,
    advertiserTextColor?:string,
    ratingBarColor?:string,
    testDevices?: Array<string>,
    onAdOpened?: Function<void>,
    onAdClosed?: Function<void>,
    onAdLeftApplication?: Function<void>,
    onAdImpression?: Function<void>,
    onAdClick?: Function<void>,
    onAdLoaded?: Function<void>,
    onAdFailedToLoad?: Function<void>,
  };

  export default class NativeAdView extends Component<NativeAdViewProps> {
   
  }
}
