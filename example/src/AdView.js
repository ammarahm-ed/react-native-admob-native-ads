import React, {useEffect, useRef, useState} from 'react';
import {ActivityIndicator, Text, View} from 'react-native';
import NativeAdView, {
  AdvertiserView,
  CallToActionView,
  HeadlineView,
  IconView,
  StarRatingView,
  StoreView,
  TaglineView,
} from 'react-native-admob-native-ads';
import {MediaView} from './MediaView';
import {adUnitIDs, Logger} from './utils';

export const AdView = ({media, type}) => {
  const [aspectRatio, setAspectRatio] = useState(1);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(false);
  const nativeAdRef = useRef();

  const onAdFailedToLoad = (event) => {
    setError(true);
    setLoading(false);
    Logger('AD', 'FAILED', event.error.message);
  };

  const onAdLoaded = () => {
    Logger('AD', 'LOADED', 'Ad has loaded successfully');
  };

  const onAdClicked = () => {
    Logger('AD', 'CLICK', 'User has clicked the Ad');
  };

  const onAdImpression = () => {
    Logger('AD', 'IMPRESSION', 'Ad impression recorded');
  };

  const onUnifiedNativeAdLoaded = (event) => {
    Logger('AD', 'RECIEVED', 'Ad impression recorded', event);
    setLoading(false);
    setAspectRatio(event.aspectRatio);
  };

  const onAdLeftApplication = () => {
    Logger('AD', 'LEFT', 'Ad left application');
  };

  useEffect(() => {
    setLoading(true);
    nativeAdRef.current?.loadAd();
  }, [type]);

  return (
    <NativeAdView
      ref={nativeAdRef}
      onAdLoaded={onAdLoaded}
      onAdFailedToLoad={onAdFailedToLoad}
      onAdLeftApplication={onAdLeftApplication}
      onAdClicked={onAdClicked}
      onAdImpression={onAdImpression}
      onUnifiedNativeAdLoaded={onUnifiedNativeAdLoaded}
      refreshInterval={60000 * 2}
      style={{
        width: '98%',
        alignSelf: 'center',
      }}
      adUnitID={type === 'image' ? adUnitIDs.image : adUnitIDs.video} // REPLACE WITH NATIVE_AD_VIDEO_ID for video ads.
    >
      <View
        style={{
          width: '100%',
        }}>
        <View
          style={{
            width: '100%',
            height: 90,
            backgroundColor: '#f0f0f0',
            position: 'absolute',
            justifyContent: 'center',
            alignItems: 'center',
            opacity: !loading && !error ? 0 : 1,
          }}>
          {loading && <ActivityIndicator size={16} color="#a9a9a9" />}
          {error && <Text style={{color: '#a9a9a9'}}>:-(</Text>}
        </View>

        <View
          style={{
            height: 100,
            width: '100%',
            flexDirection: 'row',
            justifyContent: 'space-between',
            alignItems: 'center',
            paddingHorizontal: 10,
            opacity: loading || error ? 0 : 1,
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
              numberOfLines={2}
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

            <View
              style={{
                flexDirection: 'row',
                alignItems: 'center',
              }}>
              <StoreView
                style={{
                  fontSize: 12,
                }}
              />
              <StarRatingView
                starSize={12}
                fullStarColor="orange"
                emptyStarColor="gray"
                containerStyle={{
                  width: 65,
                  marginLeft: 10,
                }}
              />
            </View>
          </View>

          <CallToActionView
            style={{
              minHeight: 45,
              paddingHorizontal: 12,
              backgroundColor: 'purple',
              justifyContent: 'center',
              alignItems: 'center',
              borderRadius: 5,
              elevation: 10,
              maxWidth: 100,
            }}
            allCaps
            textStyle={{
              color: 'white',
              fontSize: 13,
              flexWrap: 'wrap',
              textAlign: 'center',
            }}
          />
        </View>

        {media ? <MediaView aspectRatio={aspectRatio} /> : null}
      </View>
    </NativeAdView>
  );
};
