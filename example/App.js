import React from 'react';
import {View} from 'react-native';
import NativeAdView from 'react-native-admob-native-ads';

const App = () => {
  const _onAdFailedToLoad = event => {
    console.log(event);
  };

  const _onAdLoaded = () => {
    console.log('Ad has loaded');
  };

  return (
    <>
      <View
        style={{
          justifyContent: 'center',
          flex: 1,
          paddingHorizontal: 10,
          alignItems: 'center',
        }}>

        <NativeAdView
          adSize="large" // Change to small or medium.
          onAdLoaded={_onAdLoaded}
          onAdFailedToLoad={_onAdFailedToLoad}
          buttonStyle={{
            borderRadius:5,
            textColor:'#008BBA',
            backgroundColor:'#ffffff',
            borderWidth:2,
            borderColor:'#008BBA',
          }}
          headlineTextColor="#000000"
          descriptionTextColor="#a9a9a9"
          advertiserTextColor="#a9a9a9"
          backgroundStyle={{
            borderWidth:2,
            borderRadius:10,
            borderColor:"#008BBA",
            backgroundColor:"#ffffff"
          }}
          style={{
            width: '100%',
          }}
          adUnitID="ca-app-pub-3940256099942544/3986624511"
        />
      </View>
    </>
  );
};

export default App;
