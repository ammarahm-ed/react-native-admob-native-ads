@import React;
@import GoogleMobileAds;

@interface RNGADMediaView: UIView <GADVideoControllerDelegate>

@property (nonatomic) BOOL *pause;
@property (nonatomic) BOOL *muted;

@property (nonatomic, copy) RCTDirectEventBlock onVideoPlay;
@property (nonatomic, copy) RCTDirectEventBlock onVideoPause;
@property (nonatomic, copy) RCTDirectEventBlock onVideoMute;
@property (nonatomic, copy) RCTDirectEventBlock onVideoStart;
@property (nonatomic, copy) RCTDirectEventBlock onVideoEnd;
@property (nonatomic, copy) RCTDirectEventBlock onVideoProgress;

- (void)getCurrentProgress;

@end
