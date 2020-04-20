import React from "react";
import { View, Text } from "react-native";

const AdBadge = (props) => {
  return (
    <View
      style={[
        {
          width: "100%",
          height: 15,
          width:15,
          borderWidth: 1,
          borderRadius: 2.5,
          borderColor: "green",
          position:'absolute',
          left:0,
          top:0
         
        },
        props.style,
      ]}
    >
      <Text
        style={[
          {
            color: "green",
            fontSize:8
          },
          props.textStyle,
        ]}
      >
       {props.allCaps ? 'AD' : 'Ad'}
      </Text>
    </View>
  );
};

export default AdBadge;
