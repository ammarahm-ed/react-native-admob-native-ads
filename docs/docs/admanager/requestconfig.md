---
id: requestconfig-1
title: setRequestConfiguration
sidebar_label: setRequestConfiguration
---

`setRequestConfiguration` is a function that configures Ad requests and initializes Mobile Ads SDK. You need to call this function before you load any Ads. Generally, you call this function when the root component of your app is mounted.

`setRequestConfiguration` returns `Promise<MediationAdapterStatus[]>` which is each mediation adapter's initialization status.

### Properties

| Name                         | Type               | Required |
|------------------------------|--------------------|----------|
| testDeviceIds                | `Array<string>`    | no       |
| maxAdContentRating           | Ad Content Ratings | no       |
| tagForChildDirectedTreatment | `boolean`          | no       |
| tagForUnderAgeConsent        | `boolean`          | no       |
| trackingAuthorized           | `boolean`          | no       |

#### `trackingAuthorized`

If you are building for iOS 14 or later, Facebook requires that you explicitly set their Advertising Tracking Enabled flag. Use [react-native-tracking-transparency](https://github.com/mrousavy/react-native-tracking-transparency) to acquire permission status for tracking, and pass it to `trackingAuthorized` property.

### Ad Content Ratings

| Type        | Description                                                                                                                                       |
|-------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| G           | "General audiences." Content suitable for all audiences, including families and children.                                                         |
| MA          | "Mature audiences." Content suitable only for mature audiences; includes topics such as alcohol, gambling, sexual content, and weapons.           |
| PG          | "Parental guidance." Content suitable for most audiences with parental guidance, including topics like non-realistic, cartoonish violence.        |
| T           | "Teen." Content suitable for teen and older audiences, including topics such as general health, social networks, scary imagery, and fight sports. |
| UNSPECIFIED | Set default value to ""                                                                                                                           |

### MediationAdapterStatus

| Name        | Type           |
|-------------|----------------|
| name        | `string`       |
| description | `string`       |
| state       | `AdapterState` |

#### `AdapterState`

`enum AdapterState { NOT_READY, READY }`

### Usage example

```js
const config = {
  testDeviceIds: ["YOUR_TEST_DEVICE_ID"],
  maxAdContetRating: "MA",
  tagForChildDirectedTreatment: false,
  tagForUnderAgeConsent: false,
};

export default function App() {
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const init = async () => {
      const trackingStatus = await requestTrackingPermission();

      let trackingAuthorized = false;
      if (trackingStatus === "authorized" || trackingStatus === "unavailable") {
        trackingAuthorized = true;
      }

      await AdManager.setRequestConfiguration({
        ...config,
        trackingAuthorized,
      });

      setLoading(false);
    };

    init();
  }, []);

  return (/* Your App code */)
```
