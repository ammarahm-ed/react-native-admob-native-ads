---
id: adbadge
title: AdBadge
sidebar_label: AdBadge
---

Renders a small {Ad} badge on top-left corner of your ad showing the user that this is an Ad.

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

## props

### `style:ViewStyle`

Style the outer `View` Component.

### `textStyle:TextStyle`

Style the inner `Text` Component

### `allCaps`

| Type      | Required | Platform |
| --------- | -------- | -------- |
| `boolean` | no       | All      |

Whether all text should be in capital letters

:::caution

If you do not render this view in your ad, your Admob account can get suspended or even banned for not telling your app users that what they are seeing is an Ad.

:::
