---
id: media
title: NativeMediaView
sidebar_label: NativeMediaView
---

Renders the `NativeMediaView` used for displaying video & image assets of the ad.

```jsx
import { NativeMediaView } from "react-native-admob-native-ads";

<MediaView
  style={{
    width: "100%",
    height: 250,
  }}
/>;
```

## props

### `style:ViewStyle`

Style the outer `MediaView` Component.

### `muted`

Mute/unmute the video.

| Type      | Required | Platform |
| --------- | -------- | -------- |
| `boolean` | no       | All      |

`default:false`

### `pause`

Pause/play the video

| Type      | Required | Platform |
| --------- | -------- | -------- |
| `boolean` | no       | All      |

`default:false`

## Events

### `onVideoStart`

Called when the video starts playing.

### `onVideoEnd`

Called when the video has ended.

### `onVideoPause`

Called when the video is paused.

### `onVideoPlay`

Called when the video starts playing.

### `onVideoProgress`

Returns with the `duration` and `currentTime` when the video is playing.

| Type       | Required | Platform |
| ---------- | -------- | -------- |
| `Progress` | no       | All      |

`Progress:{ duration: string, currentTime: string }`

### `onVideoMute`

Called when the video is muted/unmuted. Returns current muted state.
