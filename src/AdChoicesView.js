
import React from "react";
import {
  requireNativeComponent,
} from "react-native";

const AdChoicesView = () => {

  return (
    <AdsChoicesView/>
  );
}

const AdsChoicesView = requireNativeComponent(
  "AdChoicesView",
  MediaView
);

export default AdChoicesView;
