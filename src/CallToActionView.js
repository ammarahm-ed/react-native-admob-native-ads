import React, { useCallback, useContext, useEffect, useRef } from "react";
import {
  findNodeHandle,
  requireNativeComponent,
  Platform,
  StyleSheet,
  Text,
  TouchableOpacity,
  View
} from "react-native";
import { NativeAdContext } from "./context";

const CallToActionView = ({
  style,
  allowFontScaling = true,
  textStyle,
  allCaps,
}) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const callToActionRef = useRef();
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;

    nativeAdView.setNativeProps({
      callToAction: findNodeHandle(callToActionRef.current),
    });
  }, [nativeAdView, callToActionRef]);

  useEffect(() => {
    _onLayout();
  }, [nativeAd, nativeAdView]);

  const renderText = (
    <Text allowFontScaling={allowFontScaling} style={[styles.text, textStyle]}>
      {nativeAd
        ? allCaps
          ? nativeAd.callToAction?.toUpperCase()
          : nativeAd.callToAction
        : null}
    </Text>
  );

  return (
    <View>
      <TouchableOpacity style={style}>
        {renderText}
      </TouchableOpacity>
      <View style={styles.fakeButtonWrapper} pointerEvents="box-none">
        <FakeButton style={styles.fakeButton} ref={callToActionRef} onLayout={_onLayout} />
      </View>
    </View>
  );
};

const FakeButton =
  Platform.OS === 'android'
    ? requireNativeComponent("RNAdmobButton")
    : TouchableOpacity

const styles = StyleSheet.create({
  text: {
    textAlign: 'center'
  },
  fakeButtonWrapper: {
    height: '100%',
    opacity: 0,
    position: 'absolute',
    width: '100%',
  },
  fakeButton: {
    height: '100%',
  }
});

export default CallToActionView;
