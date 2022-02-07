---
id: calltoaction
title: CallToActionView
sidebar_label: CallToActionView
---

Renders a CallToAction Button

```jsx
import { CallToActionView } from "react-native-admob-native-ads";

<CallToActionView
  style={{
    height: 45,
    paddingHorizontal: 12,
    backgroundColor: "purple",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 5,
    elevation: 10,
  }}
  textStyle={{ color: "white", fontSize: 14 }}
/>;
```

## props

### `style:ViewStyle`

Style the outer `View` Component.

### `textStyle:TextStyle`

Style the inner `Text` Component

### `allowFontScaling`

Allow font scaling on text

### `allCaps`

| Type      | Required | Platform |
| --------- | -------- | -------- |
| `boolean` | no       | All      |

Whether all text should be in capital letters
