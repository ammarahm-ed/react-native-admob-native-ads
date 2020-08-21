import React, {useState} from 'react';
import {Platform, View, Dimensions} from 'react-native';
import NativeAdView, {
  AdvertiserView,
  CallToActionView,
  HeadlineView,
  IconView,
  StarRatingView,
  TaglineView,
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

export const AdView = ({media}) => {
  const [aspectRatio, setAspectRatio] = useState(1);
  const [adLoaded, setAdLoaded] = useState(false);
  const _onAdFailedToLoad = (event) => {
    console.log(event.nativeEvent);
    setAdLoaded(false);
  };

  const _onAdLoaded = () => {
    console.log('Ad has loaded successfully');
  };

  const _onAdClicked = () => {
    console.log('User has clicked the ad');
  };

  const _onAdImpression = () => {
    console.log('Ad impressionr recorded');
  };

  const _onUnifiedNativeAdLoaded = (event) => {
    console.log(event);
    console.log('Views have populated with the Ad');
    console.log(event.aspectRatio);
    setAdLoaded(true);
    setAspectRatio(event.aspectRatio);
  };

  return (
    <NativeAdView
      onAdLoaded={_onAdLoaded}
      onAdFailedToLoad={_onAdFailedToLoad}
      onAdLeftApplication={() => {
        console.log('ad has left the application');
      }}
      onAdClicked={_onAdClicked}
      onAdImpression={_onAdImpression}
      onUnifiedNativeAdLoaded={_onUnifiedNativeAdLoaded}
      refreshInterval={60000 * 2}
      style={{
        width: '98%',
        alignSelf: 'center',
        marginVertical: 10,
      }}
      adUnitID={NATIVE_AD_ID} // REPLACE WITH NATIVE_AD_VIDEO_ID for video ads.
    >
      <View
        style={{
          width: '100%',
        }}>
        <View
          style={{
            height: 100,
            width: '100%',
            flexDirection: 'row',
            justifyContent: 'space-between',
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
              hello="abc"
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

            <StarRatingView />
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

        {media ? (
          <MediaView
            style={{
              width: Dimensions.get('window').width - 20,
              height: Dimensions.get('window').width / aspectRatio,
              backgroundColor: 'white',
            }}
          />
        ) : null}
      </View>
    </NativeAdView>
  );
};
