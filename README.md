<div align="center">
<img src="https://i.imgur.com/4DZhFR2.png" ></img>
</div>
<div align="center">
	<p>
		A simple and robust library for displaying <a href="https://developers.google.com/admob/android/native/start">Admob Native Ads</a> in your React Native App. 
	</p>
<div
align="center"
style="width:100%;"
>
<a
href="https://github.com/ammarahm-ed/react-native-admob-native-ads/pulls"
target="_blank"
>
<img  src="https://img.shields.io/badge/PRs-welcome-green"/>
</a>
<a
href="https://www.npmjs.com/package/react-native-admob-native-ads"
target="_blank"
>
<img src="https://img.shields.io/npm/v/react-native-admob-native-ads?color=green"/>
</a>
<a
href="https://www.npmjs.com/package/react-native-admob-native-ads"
target="_blank"
>
<img  src="https://img.shields.io/npm/dt/react-native-admob-native-ads?color=green"/>
</a> 
</div>
</div
<p>
If you are working on a React Native Application, you might have felt limited when it comes to displaying ads that look beautiful and match the app design. Not just displaying ads but making them look as good as your app design (Not the old banner and interstitials).

This library aims to solve that problem by providing complete support for Admob Native Ads which is dead simple and easy to use. Now you can create your own ads from ground up using custom React Native Views.

</p>

### iOS

<img
src="https://i.imgur.com/DOaoU1t.png"
/>
<img
src="https://i.imgur.com/yX5GKhN.png"
/>

### Android

<img
 width="300"
src="https://i.imgur.com/sEKEoma.png"
/>
<img
  width="300"
src="https://i.imgur.com/OVmEZzi.png"
/>
<img
  width="250"
src="https://i.imgur.com/ci3U0ZM.png"
/>
<img
  width="250"
src="https://i.imgur.com/JUxap7Y.png"
/>
<img
  width="300"
src="https://i.imgur.com/SiY3JeN.png"
/>

<div align="center">
<h2> 💫 Features</h2>
</div>
<p align="center">

1.  [Admob Native Ads](https://developers.google.com/admob/android/native/start)
2.  Cross Platform (iOS and Android)
3.  Identical Working on both platforms
4.  You can create your ads from ground up as you desire, **no limits.**
5.  No need to manage any .xml or .xib layout files!
6.  AutoRefresh ad at specific intervals
7.  **Support for Video Ads**

</p>

<div align="center">
<h2>🚀 Try out the example app!</h2>
</div>
To run the example app clone the project

    git clone https://github.com/ammarahm-ed/react-native-admob-native-ads.git

then run `yarn or npm install` in the example folder and finally to run the example app:

    react-native run-android

<div align="center">
<h2> 😋 Installation Guide </h2>
</div>

    npm install react-native-admob-native-ads --save

or if you use yarn:

    yarn add react-native-admob-native-ads

### iOS Setup

Follow the guide to add [Google Mobile Ads SDK](https://developers.google.com/admob/ios/quick-start#import_the_mobile_ads_sdk) to your Xcode project. Also don't forget to update your info.plist file to add AppID.

### Android Setup

Add your AdMob App ID to `AndroidManifest.xml`, as described in the [Google Mobile Ads SDK documentation](https://developers.google.com/admob/android/quick-start#update_your_androidmanifestxml).

<div align="center">
<h2>Basic Usage Example</h2>
</div>
This example demonstrates creation of a simple Banner Ad.  For complete usage, see the example project.

```jsx
import NativeAdView, {
  CallToActionView,
  IconView,
  HeadlineView,
  TaglineView,
  AdvertiserView,
  HeaderView,
} from "react-native-admob-native-ads";

return (
  <>
    <View
      style={{
        flex: 1,
      }}
    >
      <NativeAdView
        style={{
          width: "95%",
          alignSelf: "center",
          height: 100,
        }}
        adUnitID="ca-app-pub-3940256099942544/2247696110" // TEST adUnitID
      >
        <View
          style={{
            height: 100,
            width: "100%",
            backgroundColor: "white",
          }}
        >
          <HeaderView />
          <View
            style={{
              height: 100,
              width: "100%",
              flexDirection: "row",
              justifyContent: "flex-start",
              alignItems: "center",
              paddingHorizontal: 10,
            }}
          >
            <IconView
              style={{
                width: 60,
                height: 60,
              }}
            />
            <View
              style={{
                width: "65%",
                maxWidth: "65%",
                paddingHorizontal: 6,
              }}
            >
              <HeadlineView
                style={{
                  fontWeight: "bold",
                  fontSize: 13,
                }}
              />
              <TaglineView
                numberOfLines={1}
                style={{
                  fontSize: 11,
                }}
              />
              <AdvertiserView
                style={{
                  fontSize: 10,
                  color: "gray",
                }}
              />
            </View>

            <CallToActionView
              style={{
                height: 45,
                paddingHorizontal: 12,
                backgroundColor: "purple",
                justifyContent: "center",
                alignItems: "center",
                borderRadius: 5,
                elevation: 10,
              }}
              textStyle={{ color: "white", fontSize: 14 }}
            />
          </View>
        </View>
      </NativeAdView>
    </View>
  </>
);
```

<div align="center">
<h1>📃 Reference</h1>
</div>

## NativeAdView

NativeAdView will wrap all your views related to the ad and provides a context through which all the Views get their respective information and load it automatically. It has the following properties to it.

```jsx
<NativeAdView
  style={{
    width: "95%",
    alignSelf: "center",
    height: 100,
  }}
  adUnitID="ca-app-pub-3940256099942544/2247696110" // TEST adUnitID
>
  <View
    style={{
      height: 100,
      width: "100%",
      backgroundColor: "white",
    }}
  >
    // Everything else
  </View>
</NativeAdView>
```

### Props

#### `adUnitID`

Set Ad Unit ID for Native Advanced Ads that you created on your AdMob account.

| Type     | Required | Platform |
| -------- | -------- | -------- |
| `string` | Yes      | All      |

#

#### `testDevices`

Set testDevices during testing ads or during development.

| Type            | Required | Platform |
| --------------- | -------- | -------- |
| `Array<string>` | no       | All      |

#

#### `enableTestMode`

Setting this to true will load a placeholder ad incase you have no internet etc so you can design your ad as you want to with ease. Remember to set `adUnitID` to null when using this so the placeholder ad is not replaced by a real ad.

| Type      | Required | Platform |
| --------- | -------- | -------- |
| `boolean` | no       | All      |

#

#### `refreshInterval`

Time in ms after which a new ad should be requested from the server.

| Type     | Required | Default  | Platform |
| -------- | -------- | -------- | -------- |
| `number` | no       | 60000 ms | All      |

#

## AdBadge

Renders a small {Ad} badge on top-left corner of your ad.

```jsx
import { AdBadge } from "react-native-admob-native-ads";

<AdBadge
  style={{
    width: 15,
    height: 15,
    borderWidth: 1,
    borderRadius: 2,
    borderColor: "green",
  }}
  textStyle={{
    fontSize: 9,
    color: "green",
  }}
/>;
```

### `props`

Style the outer `View` Component.

#### `style:ViewStyle`

Style the inner `Text` Component

#### `textStyle:TextStyle`

## HeadlineView

Renders the headline or title for the ad recieved from server.

```jsx
import { HeadlineView } from "react-native-admob-native-ads";

<HeadlineView
  style={{
    fontWeight: "bold",
    fontSize: 13,
  }}
/>;
```
### props
Inherits all the props from Text Component.

## TaglineView

Renders the description for the ad recieved from server.

```jsx
import { TaglineView } from "react-native-admob-native-ads";

<TaglineView
  style={{
    fontWeight: "bold",
    fontSize: 12,
  }}
/>;
```
### props
Inherits all the props from Text Component.

## AdvertiserView

Renders the advertiser name for the ad recieved from server.

```jsx
import { AdvertiserView } from "react-native-admob-native-ads";

<AdvertiserView
  style={{
    fontWeight: "bold",
    fontSize: 10,
  }}
/>;
```
### props
Inherits all the props from Text Component.

## StoreView

Renders the name of the store (Google Playstore / AppStore) if the ad is for an app.

```jsx
import { StoreView } from "react-native-admob-native-ads";

<StoreView
  style={{
    fontWeight: "bold",
    fontSize: 10,
  }}
/>;
```
### props
Inherits all the props from Text Component.

## PriceView

Renders the price if the ad is from a paid service/app.

```jsx
import { PriceView } from "react-native-admob-native-ads";

<PriceView
  style={{
    fontWeight: "bold",
    fontSize: 10,
  }}
/>;
```
### props
Inherits all the props from Text Component.

## StarRatingView

Renders the star rating if the ad is for an app on Google Playstore or AppStore.

```jsx
import { StarRatingView } from "react-native-admob-native-ads";

<StarRatingView
  maxStars={5}// Always keep it to 5
/>;
```
### props
Inherits all the props from [react-native-star-rating](https://github.com/djchie/react-native-star-rating) library.

**Note:** Do not set the `rating` prop. This is handled automatically.

## ImageView

Renders an Image for the ad recieved from server.

```jsx
import { ImageView } from "react-native-admob-native-ads";

<ImageView
  style={{
    width:'100%',
    height:250
  }}
/>;
```
### props
Inherits all the props from Image Component.

## MediaView

Renders the MediaView used for displaying video & image both.

```jsx
import { MediaView } from "react-native-admob-native-ads";

<MediaView
  style={{
    width:'100%',
    height:250
  }}
/>;
```
### props

`style:ViewStyle`


## Events

All events are available through props.The following event are available on both Android and iOS:

#### `onUnifiedNativeAdLoaded`

This event return a data object which contains all the images and text etc. related to the ad incase you need it. Usually you wont need this because everything is loaded automatically.

#### `onAdFailedToLoad`

Called when ad has failed to load and returns reason due to which ad was not loaded.

#### `onAdLoaded`

Called when ad has successfully loaded without any errors.

#### `onAdOpened`

Called when ad is opened.

#### `onAdClosed`

Called when ad is closed.

#### `onAdLeftApplication`

Called when ad is loaded but user has left the application

#### `onAdImpression`

User impression has been recorded

#### `onAdClicked`

User has clicked on the ad.

#

## Contributing

There are multiple ways in which you can contribute to this library. Feel free to open an issue if you have an idea in mind or if you have found a bug.

If the templates do not suit your requirments and you decide to make your own templates, feel free to submit them here and I will add them in the library.

## Find this library useful? ❤️

Support it by joining **stargazers** for this repository. ⭐️ and follow me for my next creations!

### MIT Licensed
