
import React from "react";
import {
  requireNativeComponent,
} from "react-native";

const MediaView = () => {
  return (
    <AdMediaView/>
  );
}

const AdMediaView = requireNativeComponent(
  "MediaView",
  MediaView
);

export default MediaView;
