import React from 'react';
import {Dimensions} from 'react-native';
import {NativeMediaView} from 'react-native-admob-native-ads';

export const MediaView = ({aspectRatio = 1.5}) => {
  const onVideoPlay = () => {
    console.log('video is now playing');
  };

  const onVideoProgress = (event) => {
    console.log(event.nativeEvent);
  };

  console.log(aspectRatio, 'AR');
  return (
    <NativeMediaView
      style={{
        width: Dimensions.get('window').width - 20,
        height: Dimensions.get('window').width / aspectRatio,
        backgroundColor: 'white',
      }}
      onVideoPlay={onVideoPlay}
      onVideoProgress={onVideoProgress}
    />
  );
};
