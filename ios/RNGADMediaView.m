#import "RNGADMediaView.h"
#import "RNNativeAdMobUtils.h"
#import <React/RCTRootView.h>
#import <React/RCTRootViewDelegate.h>
#import <React/RCTViewManager.h>
#import <React/RCTUtils.h>
#import <React/RCTAssert.h>
#import <React/RCTBridge.h>
#import <React/RCTConvert.h>
#import <React/RCTUIManager.h>
#import <React/RCTBridgeModule.h>
#import "RCTUIManagerUtils.h"
#import <React/RCTImageView.h>
@import GoogleMobileAds;

@implementation RNGADMediaView : UIView

GADMediaView *cMediaView;


- (instancetype)initWithFrame:(CGRect)frame {
  if ((self = [super initWithFrame:frame])) {
      cMediaView = [[GADMediaView alloc] init];
      [self addSubview:cMediaView];
      cMediaView.translatesAutoresizingMaskIntoConstraints = NO;
      cMediaView.frame = self.bounds;
      [NSLayoutConstraint activateConstraints:
       @[[cMediaView.topAnchor constraintEqualToAnchor:self.topAnchor],
         [cMediaView.bottomAnchor constraintEqualToAnchor:self.bottomAnchor],
         [cMediaView.leadingAnchor constraintEqualToAnchor:self.leadingAnchor],
         [cMediaView.trailingAnchor constraintEqualToAnchor:self.trailingAnchor],
       ]];
  }
  return self;
}



- (void)setPause:(BOOL *)pause {
    if (pause) {
        
        if (cMediaView.mediaContent != nil && cMediaView.mediaContent.videoController) {
            [cMediaView.mediaContent.videoController pause];
        }
    } else {
        if (cMediaView.mediaContent != nil &&  cMediaView.mediaContent.videoController) {
            [cMediaView.mediaContent.videoController play];
        }
    }
}



- (void)setMuted:(BOOL *)muted {
    if (cMediaView.mediaContent != nil && cMediaView.mediaContent.videoController) {
        [cMediaView.mediaContent.videoController setMute:muted? true : false];
    }
}

- (void)videoControllerDidPlayVideo:(GADVideoController *)videoController {
    if (self.onVideoPlay) {
        self.onVideoPlay(@{});
    }
}


- (void)videoControllerDidPauseVideo:(GADVideoController *)videoController {
    if (self.onVideoPause) {
        self.onVideoPause(@{});
    }
}

- (void)videoControllerDidMuteVideo:(GADVideoController *)videoController {
    if (self.onVideoMute) {
        self.onVideoMute(@{@"muted":@YES});
    }
}

- (void)videoControllerDidUnmuteVideo:(GADVideoController *)videoController {
    if (self.onVideoMute) {
        self.onVideoMute(@{@"muted":@NO});
    }
}

- (void)videoControllerDidEndVideoPlayback:(GADVideoController *)videoController {
    if (self.onVideoEnd) {
        self.onVideoEnd(@{});
    }
}

- (void)getCurrentProgress
{
    
    if (self.onVideoProgress) {
        if (cMediaView.mediaContent != nil && cMediaView.mediaContent.hasVideoContent && cMediaView.mediaContent.duration > 0  ) {
            self.onVideoProgress(@{
                @"duration":[NSString stringWithFormat: @"%f", cMediaView.mediaContent.duration],
                @"currentTime":[NSString stringWithFormat: @"%f", cMediaView.mediaContent.currentTime]
                                 });
        }
    }
}

@end
