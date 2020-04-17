import React from "react";
import { requireNativeComponent, Platform, View } from "react-native";

const AdWrapperView =
  Platform.OS === "android"
    ? requireNativeComponent("RNAdComponentWrapper", Wrapper)
    : null;

const Wrapper = (props) => {
  return Platform.OS === "android" ? (
    <AdWrapperView {...props} />
  ) : (
    <View {...props} />
  );
};

export default Wrapper;
