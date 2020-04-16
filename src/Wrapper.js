
import React, { createRef } from "react";
import {
  requireNativeComponent,
} from "react-native";

const Wrapper = (props) => {

  return (<AdWrapperView
    {...props}
  />
  );
}

const AdWrapperView = requireNativeComponent(
  "RNAdComponentWrapper",
  Wrapper
);

export default Wrapper;
