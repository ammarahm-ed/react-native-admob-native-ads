
import React from "react";
import {
  Text,
  View
} from "react-native";

const StoreView = ({ props }) => {
  return (<View
    style={props.style}
    nativeID="adHeadlineView"
  >
    <Text
      textStyle={props.textStyle}
    >
      {props.store}
    </Text>
  </View>);
}


export default StoreView;
