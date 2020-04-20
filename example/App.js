import React from 'react';
import {View, Platform} from 'react-native';
import NativeAdView, {
  CallToActionView,
  IconView,
  HeadlineView,
  TaglineView,
  AdvertiserView,
} from 'react-native-admob-native-ads';


const NATIVE_AD_ID = Platform.OS ==="ios"? 'ca-app-pub-3940256099942544/3986624511' : 'ca-app-pub-3940256099942544/2247696110'

const NATIVE_AD_VIDEO_ID = Platform.OS ==="ios"? 'ca-app-pub-3940256099942544/2521693316' : 'ca-app-pub-3940256099942544/1044960115'

const App = () => {
  
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
        justifyContent:'center'
      }}>
      <View>
        <NativeAdView
          onAdLoaded={_onAdLoaded}
          onAdFailedToLoad={_onAdFailedToLoad}
          onUnifiedNativeAdLoaded={() => {
           
          }}
          style={{
            width: '95%',
            alignSelf: 'center',
            height: 400,
          }}
          adUnitID={NATIVE_AD_ID}>
          <View
            style={{
              height: 400,
              width: '100%',
              backgroundColor: 'white',
            }}>
            <View
              style={{
                height: 100,
                width: '100%',
                flexDirection: 'row',
                justifyContent: 'flex-start',
                alignItems: 'center',
                paddingHorizontal: 10,
              }}>
              <IconView
                style={{
                  width: 60,
                  height: 60,
                }}
              />
              <View
                style={{
                  width: '65%',
                  maxWidth:'65%',
                  paddingHorizontal: 6,
                }}>
                <HeadlineView
                  style={{
                    fontWeight: 'bold',
                    fontSize: 13,
                  }}
                />
                <TaglineView
                  numberOfLines={1}
                  style={{
                    fontSize: 11,
                  }}
                />
                <AdvertiserView
                  style={{
                    fontSize: 10,
                    color: 'gray',
                  }}
                />
              </View>

              <CallToActionView
              style={{
                height: 45,
                paddingHorizontal: 12,
                backgroundColor: 'purple',
                justifyContent: 'center',
                alignItems: 'center',
                borderRadius: 5,
                elevation: 10,
              }}
              textStyle={{color: 'white', fontSize: 14}}
            />

            </View>
          </View>
        </NativeAdView>
      </View>
    </View>
  );
};

export default App;
