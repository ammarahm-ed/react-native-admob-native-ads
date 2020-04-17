import React, {useState} from 'react';
import {View} from 'react-native';
import NativeAdView, {
  CallToActionView,
  IconView,
  HeadlineView,
  TaglineView,
  AdvertiserView,
  HeaderView,
  MediaView,
} from 'react-native-admob-native-ads';

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
        marginTop: 10,
      }}>
      <View>
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
            width: '95%',
            alignSelf: 'center',
            height: 400,
          }}
          adUnitID="ca-app-pub-3940256099942544/2247696110">
          <View
            style={{
              height: 400,
              width: '100%',
              backgroundColor: 'white',
            }}>
            <HeaderView />
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
                  width: 70,
                  height: 70,
                }}
              />
              <View
                style={{
                  width: '75%',
                  paddingHorizontal: 6,
                }}>
                <HeadlineView
                  style={{
                    fontWeight: 'bold',
                    fontSize: 14,
                  }}
                />
                <TaglineView
                  numberOfLines={2}
                  style={{
                    fontSize: 12,
                  }}
                />
                <AdvertiserView
                  style={{
                    fontSize: 10,
                    color: 'gray',
                  }}
                />
              </View>
            </View>

            <View
              style={{
                height: 200,
                width: '100%',
              }}>
              <MediaView
                style={{
                  width: '90%',
                  height: 200,
                  alignSelf: 'center',
                  borderRadius: 10,
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
                marginTop: 10,
              }}
              textStyle={{color: 'white', fontSize: 16}}
            />


          </View>
        </NativeAdView>
      </View>
    </View>
  );
};

export default App;
