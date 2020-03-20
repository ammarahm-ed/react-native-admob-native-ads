import { Component } from "react";
import { ViewStyle } from "react-native";

interface ButtonStyleType {
    backgroundColor: string;
    textColor: string;
    borderColor: string;
    borderWidth: number;
    borderRadius: number;
}

interface BackgroundStyleType {

    backgroundColor: string;
    borderColor: string;
    borderWidth: number;
    borderRadius: number;

};

declare module "react-native-admob-native-ads" {
  export type NativeAdViewProps = {
    style?: ViewStyle;

    /**
     * adSize: Size of the ad that you want to display.
     *
     * Android: small, medium, large
     *
     * iOS: small & medium only
     *
     */
    adSize?: string;

    /**
     * adUnitID: Set Ad Unit ID for Native Advanced Ads that you created on your AdMob account.
     */

    adUnitID: string;

    /**
     * buttonStyle: style the callToAction button in Native ad according to your app look and feel.
     *
     */

    buttonStyle?: ButtonStyleType;

    /**
     * backgroundStyle: Style the background of Native Ad View.
     */

    backgroundStyle?: BackgroundStyleType;

    /**
     * Set color for the heading text of Ad.
     */

    headlineTextColor?: string;

    /**
     * Set color for the description text of Ad.
     */
    descriptionTextColor?: string;
    /**
     * Set color for the advertiser text of Ad.
     */
    advertiserTextColor?: string;
    /**
     * Set color for the rating stars of Ad.
     * Android Only.
     */
    ratingBarColor?: string;

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
    onAdFailedToLoad?: Function<void>;
  };

  export default class NativeAdView extends Component<NativeAdViewProps> {}
}
