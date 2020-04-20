import React from "react";
import { requireNativeComponent, Platform, View } from "react-native";



const Wrapper = (props) => {
  return Platform.OS === "android" ? (
    <AdWrapperView {...props} />
  ) : (
    <View {...props} />
  );
};

const AdWrapperView =
  Platform.OS === "android"
    ? requireNativeComponent("RNAdComponentWrapper", Wrapper)
    : null;

export default Wrapper;
