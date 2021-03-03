---
id: migrating
title: Migrating
sidebar_label: Migrating
---

## From 0.3.9

If you are migrating from `v0.3.9` of this library, here a the changes that you will need to make to your code.

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
