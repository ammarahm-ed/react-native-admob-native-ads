import React, { useRef, useEffect, useContext } from "react";
import { findNodeHandle, requireNativeComponent, UIManager } from "react-native";
import { NativeAdContext } from "./context";

let timers = {};

const NativeMediaView = (props) => {
  const { nativeAd, nativeAdView } = useContext(NativeAdContext);
  const adMediaView = useRef();
  let nodeHandle = null;

  const _onLayout = () => {
    if (!nativeAdView) return;
    _clearInterval();
    nodeHandle = findNodeHandle(adMediaView.current);
    nativeAdView.setNativeProps({
      mediaview: nodeHandle,
    });
  };

  useEffect(() => {
    _onLayout();
    return () => {
      _clearInterval();
    }
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
    },1000)
  }

  const _clearInterval = () => {
    clearInterval(timers[nodeHandle]);
    console.log(nodeHandle)
    timers[nodeHandle] = null;
    console.log(timers);
  }

  const onVideoPlay = () => {
    _setInterval();
    props.onVideoPlay && props.onVideoPlay()
  }
  const onVideoPause = () => {
    _clearInterval();
    props.onVideoPause && props.onVideoPause()
  }

  const onVideoEnd= () => {
    _clearInterval();
    props.onVideoEnd && props.onVideoEnd()
  }



  return (
    <AdMediaView 
    {...props} 
    onVideoPlay={onVideoPlay}
    onVideoPause={onVideoPause}
    onVideoEnd={onVideoEnd}
    ref={adMediaView} 
    onLayout={_onLayout} 
    />
  );
};

const AdMediaView = requireNativeComponent("RNGADMediaView", NativeMediaView);

export default NativeMediaView;
