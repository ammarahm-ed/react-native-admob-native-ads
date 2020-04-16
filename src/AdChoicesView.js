
import React, { createRef, useEffect, useContext } from "react";
import {
  requireNativeComponent,
  findNodeHandle,
  Text,
  Image
} from "react-native";
import { nativeAdView, NativeAdContext } from "react-native-admob-native-ads/src/context";


const adChoicesView = createRef();
const AdChoicesView = (props) => {
  const { nativeAd, setNativeAd } = useContext(NativeAdContext);


  useEffect(() => {

    let handle = findNodeHandle(adChoicesView.current);
    nativeAdView.current?.setNativeProps({
      adChoicesView:handle
    });
    
  },[nativeAd])


  return (<AdsChoicesView
    ref={adChoicesView}
  {...props}
  > 

 

  </AdsChoicesView>
  );
}

const AdsChoicesView = requireNativeComponent(
  "AdChoicesView",
  AdChoicesView
);


export default AdChoicesView;
