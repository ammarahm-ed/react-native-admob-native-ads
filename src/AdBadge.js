import React from "react";
import { View, Text, StyleSheet } from "react-native";

const AdBadge = ({allCaps,textStyle,style}) => {
  return (
    <View
      style={[
        styles.container,
       style,
      ]}
    >
      <Text
        style={[
          styles.text,
          textStyle,
        ]}
      >
        {allCaps ? 'AD' : 'AD'}
      </Text>
    </View>
  );
};

export default AdBadge;

const styles = StyleSheet.create({
  container: {
    height: 17,
    width: 21,
    borderWidth: 1,
    borderRadius: 2.5,
    left: 0,
    top: 0,
    justifyContent:'center',
    alignItems:'center',
    paddingHorizontal: 1,
    position:"absolute",

  },
  text: {
    fontSize: 12,
    fontWeight:"bold"
  }
})
