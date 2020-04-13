
import React from "react";
import {
  View,
  Image,
} from "react-native";

const ImageView = ({ props }) => {
  return (<View
    style={props.style}
    nativeID="adImageView"
  >
    <Image
      style={props.imageStyle}
      source={{uri:props.uri}}
    />
  </View>);
}

export default ImageView;
