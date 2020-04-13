
import React from "react";
import {
  Text,
  View
} from "react-native";

const BodyTextView = ({ props }) => {
  return (<View
    style={props.style}
    nativeID="adBodyView"
  >
    <Text
      textStyle={props.textStyle}
    >
      {props.text}
    </Text>
  </View>);
}


export default BodyTextView;
