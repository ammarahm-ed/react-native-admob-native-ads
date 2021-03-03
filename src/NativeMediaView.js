import React, { useRef, useEffect, useContext, useCallback } from "react";
import {
  findNodeHandle,
  Platform,
  requireNativeComponent,
  UIManager,
} from "react-native";
import { NativeAdContext } from "./context";

let timers = {};

const NativeMediaView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const adMediaView = useRef();
  let nodeHandle = null;
  const _onLayout = useCallback(() => {
    if (!nativeAdView) return;

    _clearInterval();
    nodeHandle = findNodeHandle(adMediaView.current);
    nativeAdView.setNativeProps({
      mediaview: nodeHandle,
    });
  }, [nativeAdView]);

  useEffect(() => {
    _onLayout();
    return () => {
      _clearInterval();
    };
  }, [nativeAd, nativeAdView]);

  const _setInterval = () => {
    _clearInterval();
    timers[nodeHandle] = setInterval(() => {
      if (!adMediaView.current) {
        clearInterval(timers[nodeHandle]);
        return;
      }
      UIManager.dispatchViewManagerCommand(
        findNodeHandle(adMediaView.current),
        UIManager.getViewManagerConfig("RNGADMediaView").Commands.getProgress,
        undefined
      );
    }, 1000);
  };

  const _clearInterval = () => {
    clearInterval(timers[nodeHandle]);
    timers[nodeHandle] = null;
  };

  const onVideoPlay = () => {
    _setInterval();
    props.onVideoPlay && props.onVideoPlay();
  };
  const onVideoPause = () => {
    _clearInterval();
    props.onVideoPause && props.onVideoPause();
  };

  const onVideoEnd = () => {
    _clearInterval();
    props.onVideoEnd && props.onVideoEnd();
  };

  const onVideoProgress = (event) => {
    if (Platform.OS === "android") {
      let progress = event.nativeEvent;
      let duration = parseFloat(progress.duration);
      let current = parseFloat(progress.currentTime);
      if (current + 4 > duration) {
        _clearInterval();
      }
    }
    props.onVideoProgress && props.onVideoProgress(event.nativeEvent);
  };

  const onVideoMute = (event) => {
    props.onVideoMute && props.onVideoMute(event.nativeEvent?.muted);
  };

  return (
    <AdMediaView
      {...props}
      onVideoPlay={onVideoPlay}
      onVideoPause={onVideoPause}
      onVideoEnd={onVideoEnd}
      onVideoProgress={onVideoProgress}
      onVideoMute={onVideoMute}
      ref={adMediaView}
      onLayout={_onLayout}
    />
  );
};

const AdMediaView = requireNativeComponent("RNGADMediaView", NativeMediaView);

export default NativeMediaView;
