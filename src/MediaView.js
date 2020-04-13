
import React from "react";
import {
  requireNativeComponent,
} from "react-native";



const MediaView = () => {
  return (
    <AdMobMediaView/>
  );
}

const AdMobMediaView = requireNativeComponent(
  "MediaView",
  MediaView
);

export default MediaView;
