
import React from "react";
import {
  Text,
  View,
  TouchableOpacity
} from "react-native";

const CallToActionView = ({ props }) => {
  return (<TouchableOpacity
    style={props.style}
    nativeID="adCallToAction"
  >
    <Text
      textStyle={props.textStyle}
    >
      {props.text}
    </Text>
  </TouchableOpacity>);
}

export default CallToActionView;
