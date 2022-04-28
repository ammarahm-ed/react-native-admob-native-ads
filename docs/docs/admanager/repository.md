---
id: repository-1
title: Repository
sidebar_label: Repository
---
Repositories can be used to preload ads in the app and show them to the user when they navigate to some part of your app.

### `registerRepository`
Register a repository to preload ads

#### Properties

| Name                          | Type                                                                                                                 | Required |
|-------------------------------|----------------------------------------------------------------------------------------------------------------------|----------|
| name                          | `string`                                                                                                             | no       |
| adUnitId                      | Ad Content Ratings                                                                                                   | yes      |
| numOfAds                      | `number`                                                                                                             | no       |
| requestNonPersonalizedAdsOnly | `boolean`                                                                                                            | no       |
| expirationPeriod              | `number`                                                                                                             | no       |
| mediationEnabled              | `boolean`                                                                                                            | no       |
| videoOptions                  | [VideoOptions](https://ammarahm-ed.github.io/react-native-admob-native-ads/docs/nativeadview/videooptions-7)         | no       |
| mediationOptions              | [MediationOptions](https://ammarahm-ed.github.io/react-native-admob-native-ads/docs/nativeadview/mediationoptions-5) | no       |
| targetingOptions              | [TargetingOptions](https://ammarahm-ed.github.io/react-native-admob-native-ads/docs/nativeadview/targetingoptions-6) | no       |
| adChoicesPlacement            | `"topLeft","topRight","bottomLeft","bottomRight"`                                                                    | no       |
| mediaAspectRatio              | `"any","landscape","portrait","square","unknown"`                                                                    | no       |

### `unRegisterRepository`
Unregister a repository. All preloaded ads in this repository will be destroyed.

### `resetCache`
Reset all ad repositories.

### `hasAd`
Check if there is ad in a repository.
