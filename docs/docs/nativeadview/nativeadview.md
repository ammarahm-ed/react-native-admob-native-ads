---
id: nativeadview-2
title: NativeAdView
sidebar_label: How to use
---

NativeAdView wraps all your Ad components inside it and provides a `Context` through which each component gets the ad information it requires.

## Importing

The NativeAdView is imported as the default component from the library.

```jsx
import NativeAdView from "react-native-admob-native-ads";
```

## Basic usage

```jsx
<NativeAdView adUnitID="ca-app-pub-3940256099942544/2247696110">
  <View>/* All other ad components */</View>
</NativeAdView>
```

## Loading an Ad

Ads are loaded imperitively. You need to provide a ref to the `NativeAdView` to load ads.

```jsx
const AdView = () => {
  const nativeAdViewRef = useRef();

  React.useEffect(() => {
    nativeAdViewRef.current?.loadAd();
  }, []);

  return (
    <NativeAdView
      ref={nativeAdViewRef}
      adUnitID="ca-app-pub-3940256099942544/2247696110"
    >
      <View>/* All other ad components */</View>
    </NativeAdView>
  );
};
```

:::tip

To prevent `NativeAdView` from rerendering and requesting new ads, you can wrap your component in `React.memo()` or use `React.PureComponent`.

:::

:::caution

From version `0.4.0` onwards ads do not refresh automatically to save bandwidth and reduce the amount of requests sent to Admob. You must only reload an ad when you are sure that you need to reload it.

:::

## Props

### `style`

Style for your NativeAdView.

### `adUnitID`

Set Ad Unit ID for Native Advanced Ads that you created on your AdMob account.

| Type     | Required | Platform |
| -------- | -------- | -------- |
| `string` | Yes      | All      |

:::tip

Remember that there are two types of Admob Native Advanced Ad IDs. One is for simple image ads and other is for video ads.

:::

:::caution

During development you should never use your Admob IDs. Instead you can grab the test Admob IDs for [iOS](https://developers.google.com/admob/android/test-ads) and [Android](https://developers.google.com/admob/ios/test-ads) from Google Developers Website. If you use your own Ad IDs, you must add your device to test-devices.

:::

### `enableTestMode`

Setting this to true will load a placeholder ad (Not from Admob server) incase you have no internet etc so you can design your ad as you want with ease. Remember to set the `adUnitID` to null when using this so the placeholder ad is not replaced by a real ad.

| Type      | Required | Platform |
| --------- | -------- | -------- |
| `boolean` | no       | All      |

### `adChoicesPlacement`

Placement of AdChoices can be in any of the 4 corners of the ad

**AdOptions.adChoicesPlacement**

| Value       | Description                                     |
| ----------- | ----------------------------------------------- |
| topLeft     | Show AdChoices on top right corner of the Ad    |
| topRight    | Show AdChoices on top lef corner of the Ad      |
| bottomLeft  | Show AdChoices on bottom right corner of the Ad |
| bottomRight | Show AdChoices on bottom left corner of the Ad  |

`default: topRight`

### `mediaAspectRatio`

You can specify to Admob what kind of ad you want to recieve however it is not guarenteed that you will recieve the same ad you requested.

| Value     | Description                                     |
| --------- | ----------------------------------------------- |
| unknown   | Show whatever ad is available    |
| any       | Any ads are prefferred.     |
| landscape | Landscape ads are prefferred. |
| portrait  | Portrait ads are prefferred.  |
| square    | Square ads are prefferred.  |

`default: any`

### `requestNonPersonalizedAdsOnly`

Under the Google EU User Consent Policy, you must make certain disclosures
to your users in the European Economic Area (EEA) and obtain their consent
to use cookies or other local storage, where legally required, and to use
personal data (such as AdID) to serve ads. This policy reflects the requirements
of the EU ePrivacy Directive and the General Data Protection Regulation (GDPR).

You can use library such as: https://github.com/birgernass/react-native-ad-consent
to obtain the consent or if you are using rn-firebase you can obtain the consent from
there and then pass the consent to this library. If user has selected
non-personalized-ads then pass `true` and non-personalized ads will be shown to the user.

| Type      | Required | Platform |
| --------- | -------- | -------- |
| `boolean` | no       | All      |
