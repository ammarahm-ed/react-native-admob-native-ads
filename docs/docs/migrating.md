---
id: migrating
title: Migrating Guide
sidebar_label: Migrating Guide
---

## From 0.3.9

If you are migrating from `v0.3.9` of this library, here a the changes that you will need to make to your code.

### Update Google-Mobile-Ads
Update your Google Mobile Ads Library to >=8.0.0 in Podfile.

Add these to your Podfile.

```
pod 'Google-Mobile-Ads-SDK'
pod 'GoogleMobileAdsMediationFacebook'
```
Then run `pod update` & `pod install`

### Manually load ads

Ads are to be loaded using the `ref` on `NativeAdView`

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

### Rename `MediaView` to `NativeMediaView`
`MediaView` has been renamed to `NativeMediaView` to avoid conflicts with other libraries

### Use `buttonAndroidStyle` for `CallToActionView`
To style the `CallToAction` button on Android, you should use `buttonAndroidStyle` Prop.
