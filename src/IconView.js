
import React from "react";
import {
  View,
  Image
} from "react-native";

const IconView = ({ props }) => {
  return (<View
    style={props.style}
    nativeID="adIconView"
  >
    <Image
      style={[{ width: 128, height: 128 }, props.imageStyle]}
      source={{uri:props.uri}}
    />
  </View>);
}

export default IconView;
