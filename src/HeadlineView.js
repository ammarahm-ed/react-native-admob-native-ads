
import React from "react";
import {
  Text,
  View
} from "react-native";

const HeadlineView = (props) => {
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


export default HeadlineView;
