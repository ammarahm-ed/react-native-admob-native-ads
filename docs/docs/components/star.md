---
id: star
title: StarRatingView
sidebar_label: StarRatingView
---

Renders the star rating if the ad is for an app on Google Playstore or AppStore.

```jsx
import { StarRatingView } from "react-native-admob-native-ads";

<StarRatingView />;
```

## props

### `style:ViewStyle`

Style the outer `View` Component.

### `size`

Size of the star icon.

| Type     | Required | Platform |
| -------- | -------- | -------- |
| `number` | no       | All      |

`default:15`

### `iconSet`

Icon family of the icons you are using.

| Type    | Required | Platform |
| ------- | -------- | -------- |
| IconSet | no       | All      |

`default: "MaterialCommunityIcons"`

`IconSet: "Entypo" | "EvilIcons" | "Feather" | "Fontisto" | "FontAwesome" | "Foundation" | "Ionicons" | "MaterialIcons" | "MaterialCommunityIcons" | "Octicons" | "Zocial" | "SimpleLineIcons" `

### `fullIcon`

Icon to use for full star rating.

| Type     | Required | Platform |
| -------- | -------- | -------- |
| `string` | no       | All      |

`default: "star-outline"`

### `halfIcon`

Icon to use for half star rating.

| Type     | Required | Platform |
| -------- | -------- | -------- |
| `string` | no       | All      |

`default: "star-half"`

### `emptyIcon`

Icon to use for empty star.

| Type     | Required | Platform |
| -------- | -------- | -------- |
| `string` | no       | All      |

`default: "star-outline"`
