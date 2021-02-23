---
id: installation-3
title: Installation
sidebar_label: Installation
---

If you are using `react-native >= 0.60` you just need to do a simple:

    npm install react-native-admob-native-ads --save

or if you use yarn:

    yarn add react-native-admob-native-ads

You also need to install `react-native-vector-icons`:

```bash
yarn add react-native-vector-icons
```

Don't forget to setup [react-native-vector-icons ](https://github.com/oblador/react-native-vector-icons) as the guide states for iOS & Android

## Android Setup

Add your AdMob App ID to `AndroidManifest.xml`, as described in the [Google Mobile Ads SDK documentation](https://developers.google.com/admob/android/quick-start#update_your_androidmanifestxml).

## iOS Setup

Follow the guide to add [Google Mobile Ads SDK](https://developers.google.com/admob/ios/quick-start#import_the_mobile_ads_sdk) to your Xcode project. Also don't forget to update your info.plist file to add AppID.

After configuring your project as mentioned in the above guide you must run:

```bash
pod install
```

## Requesting IDFA on iOS 14

On iOS 14 onwards, you need to request IDFA access through App Tracking Transparency Dialog to show targeted ads to the user. For that you can use [react-native-tracking-transparency](https://github.com/mrousavy/react-native-tracking-transparency).
