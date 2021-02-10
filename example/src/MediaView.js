import React from 'react';
import {Dimensions} from 'react-native';
import {NativeMediaView} from 'react-native-admob-native-ads';
import {Logger} from './utils';

export const MediaView = ({aspectRatio = 1.5}) => {

  const onVideoPlay = () => {
    Logger('VIDEO', 'PLAY', 'Video is now playing');
  };
  
  const onVideoPause = () => {
    Logger('VIDEO', 'PAUSED', 'Video is now paused');
  };

  const onVideoProgress = (event) => {
    Logger('VIDEO', 'PROGRESS UPDATE', event);
  };

  const onVideoEnd = () => {
    Logger('VIDEO', 'ENDED', 'Video end reached');
  };

  const onVideoMute = (muted) => {
    Logger('VIDEO', 'MUTE', muted);
  };

  return (
    <NativeMediaView
      style={{
        width: Dimensions.get('window').width - 20,
        height: Dimensions.get('window').width / aspectRatio,
        backgroundColor: 'white',
      }}
      onVideoPause={onVideoPause}
      onVideoPlay={onVideoPlay}
      onVideoEnd={onVideoEnd}
      onVideoProgress={onVideoProgress}
      onVideoMute={onVideoMute}
    />
  );
};
