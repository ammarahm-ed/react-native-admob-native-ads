import React from "react";
import { StyleSheet, Text } from "react-native";

const AdBadge = ({allCaps,textStyle,style}) => {
  return (
    <Text
    style={[
      styles.text,
      textStyle,
    ]}
  >
    {allCaps ? 'AD' : 'AD'}
  </Text>
  );
};

export default AdBadge;

const styles = StyleSheet.create({
  text: {
    color: "green",
    fontSize: 10,
    fontWeight:"bold",
    position:"absolute", 
    left: 1,
    top: 1,
    zIndex: 999
  }
})
