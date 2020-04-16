import React, { useState } from 'react';
import { Dimensions, View, } from 'react-native';
import { CallToActionView, HeadlineView, IconView, NativeAdView, StoreView, TaglineView } from 'react-native-admob-native-ads';

const App = () => {
  const [adLoaded, setAdLoaded] = useState(false);
  const _onAdFailedToLoad = event => {
    console.log(event.nativeEvent);
  };

  const _onAdLoaded = () => {

    console.log('Ad has loaded');

  };

  return (
    <View
      style={{
        height: '100%',
        width: '100%',
        backgroundColor: 'red'

      }}
    >
      <NativeAdView
        onAdLoaded={_onAdLoaded}
        onAdFailedToLoad={_onAdFailedToLoad}
        onAdClicked={() => {
          //alert('clicked me hey');
        }}
        onUnifiedNativeAdLoaded={() => {
          setAdLoaded(true);
        }}
        style={{
          width: Dimensions.get('window').width,
          height: 400,
          backgroundColor: 'white'
        }}
        adUnitID="ca-app-pub-3940256099942544/2247696110"
      >
        <View
          style={{
            height: 400,
            width: 400,
            backgroundColor: "yellow"
          }}
        >
          <IconView
            style={{
              width: 100,
              height: 100,
              backgroundColor: 'black'
            }}
          />
          <HeadlineView
            style={{
              height: 30,
              width: '100%'
            }}
          />
          <TaglineView
            style={{
              height: 50,
              width: '100%'
            }}
          />
          <CallToActionView
            textStyle={{
              color: 'white',
              width: '100%',
              height: '100%',
              textAlign:'center',
              textAlignVertical:"center"
            }}
            style={{
              width: 80,
              height: 50,
              backgroundColor: 'red',
              justifyContent: 'center',
              alignItems: 'center',
              borderRadius: 10

            }}

          />

          <StoreView />
          <View
            style={{
              width: 100,
              height: 50
            }}
          >
          </View>
        </View>
      </NativeAdView>
    </View>
  );
};

export default App;
