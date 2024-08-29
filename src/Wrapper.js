import React, { useContext, useEffect, useRef, useState } from "react";
import { ScrollView, StyleSheet } from "react-native";
import { NativeAdContext } from "./context";

const Wrapper = (props) => {
  const [height, setHeight] = useState(0);
  const ref = useRef();
  const { nativeAd } = useContext(NativeAdContext);

  useEffect(() => {
    setTimeout(() => {
      if (nativeAd && ref.current !== null) {
        ref.current?.scrollTo({
          x: 0,
          y: 1,
          animated: true,
        });
      }
    }, 1000);
  }, [nativeAd]);

  return (
    <ScrollView
      {...props}
      ref={ref}
      onLayout={(event) => {
        props.onLayout?.(event);
        const height = event.nativeEvent.layout.height;
        setTimeout(() => setHeight(height));
      }}
      style={[
        {
          height: height === 0 ? "auto" : height,
        },
        styles.container,
      ]}
      contentContainerStyle={{
        height: height === 0 ? "auto" : height + 1,
      }}
      scrollEnabled={false}
    />
  );
};

export default Wrapper;

const styles = StyleSheet.create({
  container: {
    backgroundColor: "transparent",
  },
});
