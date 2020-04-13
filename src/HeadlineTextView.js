
import React from "react";
import {
  Text,
  View
} from "react-native";

const HeadlineTextView = ({ props }) => {
  return (<View
    style={props.style}
    nativeID="adHeadlineView"
  >
    <Text
      textStyle={props.textStyle}
    >
      {props.text}
    </Text>
  </View>);
}


export default HeadlineTextView;
