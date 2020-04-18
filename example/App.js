import React, {useState} from 'react';
import {View} from 'react-native';
import NativeAdView, {
  CallToActionView,
  IconView,
  HeadlineView,
  TaglineView,
  AdvertiserView,
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
            height: 100,
          }}
          adUnitID="ca-app-pub-3940256099942544/2247696110">
          <View
            style={{
              height: 100,
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
