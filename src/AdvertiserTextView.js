
import React from "react";
import {
  Text,
  View
} from "react-native";

const AdvertiserTextView = ({ props }) => {
  return (<View
    style={props.style}
    nativeID="adAdvertiserText"
  >
    <Text
      textStyle={props.textStyle}
    >
      {props.text}
    </Text>
  </View>);
}


export default AdvertiserTextView;
