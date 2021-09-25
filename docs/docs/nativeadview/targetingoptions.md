---
id: targetingoptions-6
title: targetingOptions
sidebar_label: targetingOptions
---

The `targetingOptions` prop lets you set targets and other targeting related properties. For the purpose of completeness, the information about each has been taken from Google Developers Website.

### `publisherProvidedID`

Publisher provided user ID.

| Type     | Required | Platform |
| -------- | -------- | -------- |
| `string` | No       | All      |

### `categoryExclusions`

Array of strings used to exclude specified categories in ad results.

| Type            | Required | Platform |
| --------------- | -------- | -------- |
| `Array<string>` | No       | All      |

### `targets`

Key-value pairs used for custom targeting.

| Type            | Required | Platform |
| --------------- | -------- | -------- |
| `Array<Target>` | No       | All      |

`Target:{ key: boolean, value: string | Array<string> }`

### `requestAgent`

String that identifies the ad request’s origin. Third party libraries that reference the Mobile Ads SDK should set this property to denote the platform from which the ad request originated. For example, a third party ad network called “CoolAds network” that is mediating requests to the Mobile Ads SDK should set this property as “CoolAds”.

| Type     | Required | Platform |
| -------- | -------- | -------- |
| `string` | No       | All      |

### `keywords`

Array of keyword strings. Keywords are words or phrases describing the current user activity such as “Sports Scores” or “Football”.

| Type            | Required | Platform |
| --------------- | -------- | -------- |
| `Array<string>` | No       | All      |

### `contentUrl`

URL string for a webpage whose content matches the app’s primary content. This webpage content is used for targeting and brand safety purposes.

| Type     | Required | Platform |
| -------- | -------- | -------- |
| `string` | No       | All      |

### `neighboringContentUrls`

URL strings for non-primary web content near an ad. Promotes brand safety and allows displayed ads to have an app level rating (MA, T, PG, etc) that is more appropriate to neighboring content.

| Type            | Required | Platform |
| --------------- | -------- | -------- |
| `Array<string>` | No       | All      |
