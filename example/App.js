import React from 'react';
import {SafeAreaView, View} from 'react-native';
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
          adSize="small"
          onAdLoaded={_onAdLoaded}
          onAdFailedToLoad={_onAdFailedToLoad}
          buttonStyle={{
            borderRadius:5,
            textColor:"#E9514C",
            backgroundColor:'#ffffff',
            borderWidth:1,
            borderColor:'#008BBA',
            
          }}
          backgroundStyle={{
            borderWidth:1,
            borderRadius:5,
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
