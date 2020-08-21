import React from "react";
import { View } from "react-native";

const Wrapper = (props) => {
  return (
    <View
      {...props}
      style={{
        backgroundColor: "rgba(0,0,0,0)",
      }}
    />
  );
};

export default Wrapper;
