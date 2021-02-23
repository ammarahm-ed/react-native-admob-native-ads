---
id: events-4
title: Event props
sidebar_label: Event props
---

All events are available through props. The following event are available on both Android and iOS:

### `onUnifiedNativeAdLoaded`

This event returns a data object which contains all the ad related information incase you need it. Usually you wont need this because everything is loaded automatically in the views.

| Value                 | Description                                                                                                                          | Platform |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------------ | -------- |
| `headline:string`     | Title of the native ad.                                                                                                              | All      |
| `tagline:string`      | Description of the native ad.                                                                                                        | All      |
| `advertiser:string`   | Advertiser of the native ad.                                                                                                         | All      |
| `price:string`        | If the ad is for a paid app or service, price value.                                                                                 | All      |
| `aspectRatio:number`  | Aspect ratio of the Content loaded inside MediaView. You should use this to calculate the correct width and height of the MediaView. | All      |
| `store:string`        | If ad is for an app, then name of the store.                                                                                         | All      |
| `icon:string`         | Icon / Logo url of the adveriser or the product advertised in ad.                                                                    | All      |
| `images:Array<Image>` | Array of image urls recieved with the ad. The `Image` object contains `url`,`width` and `height` of image.                           | All      |
| `callToAction:string` | callToAction text value for the native ad.                                                                                           | All      |
| `rating:number`       | If ad is for an app, then its rating on the respective store.                                                                        | All      |
| `video:boolean` | if ad has video content or not. | All |

### `onAdFailedToLoad`

Called when ad has failed to load and returns reason due to which ad was not loaded.

### `onAdLoaded`

Called when ad has successfully loaded without any errors.

### `onAdOpened`

Called when ad is opened.

### `onAdClosed`

Called when ad is closed.

### `onAdLeftApplication`

Called when ad is loaded but user has left the application

### `onAdImpression`

User impression has been recorded

### `onAdClicked`

User has clicked on the ad.
