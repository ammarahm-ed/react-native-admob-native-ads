import React, { useState } from 'react';
import {View, Platform, Modal, ScrollView} from 'react-native';
import NativeAdView, {
  CallToActionView,
  IconView,
  HeadlineView,
  TaglineView,
  AdvertiserView,
  MediaView,
} from 'react-native-admob-native-ads';

const NATIVE_AD_ID =
  Platform.OS === 'ios'
    ? 'ca-app-pub-3940256099942544/3986624511'
    : 'ca-app-pub-3940256099942544/2247696110';

const NATIVE_AD_VIDEO_ID =
  Platform.OS === 'ios'
    ? 'ca-app-pub-3940256099942544/2521693316'
    : 'ca-app-pub-3940256099942544/1044960115';

const App = () => {
  const [aspectRatio,setAspectRatio] = useState(1);
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
        justifyContent: 'center',
      }}>
      <ScrollView>
        <NativeAdView
          onAdLoaded={_onAdLoaded}
          onAdFailedToLoad={_onAdFailedToLoad}
          onUnifiedNativeAdLoaded={event => {
            setAspectRatio(event.aspectRatio);
          }}
          style={{
            width: '95%',
            alignSelf: 'center',
            height: 900,
          }}
          adUnitID={NATIVE_AD_VIDEO_ID} // REPLACE WITH NATIVE_AD_VIDEO_ID for video ads.
        >
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
                  width: '60%',
                  maxWidth: '60%',
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
            <MediaView
              style={{
                width:400,
                height: 400/aspectRatio,
                backgroundColor: 'gray',
              }}
            />
          </View>
        </NativeAdView>
       

      </ScrollView>
    </View>
  );
};

export default App;
