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
        {allCaps ? 'AD' : 'Ad'}
      </Text>
    </View>
  );
};

export default AdBadge;

const styles = StyleSheet.create({
  container: {
    width: "100%",
    height: 15,
    width: 15,
    borderWidth: 1,
    borderRadius: 2.5,
    borderColor: "green",
    position: 'absolute',
    left: 0,
    top: 0

  },
  text: {
    color: "green",
    fontSize: 8
  }
})
