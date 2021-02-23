---
id: requestconfig-1
title: setRequestConfiguration
sidebar_label: setRequestConfiguration
---

Configure your Ad Requests during App Startup. You need to pass a single object as an argument with atleast one of the following properties

### Properties

| Name                         | Type               | Required |
| ---------------------------- | ------------------ | -------- |
| testDeviceIds                | `Array<string>`    | no       |
| maxAdContentRating           | Ad Content Ratings | no       |
| tagForChildDirectedTreatment | `boolean`          | no       |
| tagForUnderAgeConsent        | `boolean`          | no       |

### Ad Content Ratings


| Type       | Description                                                                                                                                       |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------------------------- |
| G           | "General audiences." Content suitable for all audiences, including families and children.                                                         |
| MA          | "Mature audiences." Content suitable only for mature audiences; includes topics such as alcohol, gambling, sexual content, and weapons.           |
| PG          | "Parental guidance." Content suitable for most audiences with parental guidance, including topics like non-realistic, cartoonish violence.        |
| T           | "Teen." Content suitable for teen and older audiences, including topics such as general health, social networks, scary imagery, and fight sports. |
| UNSPECIFIED | Set default value to ""                                                                                                                           |

### Usage example

```js
const config = {
  testDeviceIds: ["YOUR_TEST_DEVICE_ID"],
  maxAdContetRating: "MA",
  tagForChildDirectedTreatment: false,
  tagForUnderAgeConsent: false,
};

AdManager.setRequestConfiguration(config);
```
