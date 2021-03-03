---
id: introduction
title: Introduction
sidebar_label: Introduction
---

If you are working on a React Native Application, you might feel limited when it comes to displaying ads that look beautiful and match your app's look and feel. Usually when you show ads in your app, they are loaded inside a `WebView` and that is why you cannot modify them except changing the width and height in some cases.

## How is this library different?

We on the other hand do not load ads in a `WebView`. We use a more complex, [Native Advanced](https://developers.google.com/admob/android/native/start) ad format to display ads. We request Admob servers to send us the ad information such as images, headlines, videos etc. Then we send this information over the bridge to React Native and render it in React Native's `View` and `Text` components execpt a few exceptions where we use our own custom components.

## Can I design the ads myself?

Yes absolutely you can! There are no limiations. You can design ads that look just like your App's design and colors.

## Features

1.  [Admob Native Advanced Ads](https://developers.google.com/admob/android/native/start) format
2.  Cross Platform (iOS and Android)
3.  Identical Working on both platforms
4.  You can create your ads from ground up as you desire, **no limits.**
5.  No need to manage any .xml or .xib layout files!
6.  AutoRefresh ad at specific intervals
7.  **Support for Video Ads**
8.  Ad Mediation (Especially Facebook Ads)
9.  Ad Targeting
10. Typescript definations

## Benefits

Ads that look just like your App's design will result in better conversions and revenue for you as compared to other ad formats which look out of place and interrupt the user experience.

## How can I support the project?

- It costs me alot of time to keep the library updated and address all the bugs & issues. If this library has helped you [buy me a coffee](https://ko-fi.com/ammarahmed).
- Add a ⭐️ [star on GitHub](https://github.com/ammarahm-ed/react-native-admob-native-ads) to support the project!
- Create a GitHub issue for bug reports, feature requests, or questions
- Follow [@ammarahm-ed](https://github.com/ammarahm-ed) for announcements
