import React from "react";
import { StyleSheet, View } from "react-native";

const Wrapper = (props) => {
  return (
    <View
      {...props}
      style={styles.container}
    />
  );
};

export default Wrapper;

const styles = StyleSheet.create({
  container: {
    backgroundColor: 'transparent'
  }
})
