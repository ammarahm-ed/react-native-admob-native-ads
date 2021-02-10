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

@implementation RNGADMediaView : GADMediaView

- (void)setPause:(BOOL *)pause {
    if (pause) {
        if (self.mediaContent.videoController) {
            [self.mediaContent.videoController pause];
        }
    } else {
        if (self.mediaContent.videoController) {
            [self.mediaContent.videoController play];
        }
    }
}



- (void)setMuted:(BOOL *)muted {
    if (self.mediaContent.videoController) {
        [self.mediaContent.videoController setMute:muted? true : false];
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
        if (self.mediaContent != nil && self.mediaContent.hasVideoContent && self.mediaContent.duration > 0  ) {
            self.onVideoProgress(@{
                @"duration":[NSString stringWithFormat: @"%f", self.mediaContent.duration],
                @"currentTime":[NSString stringWithFormat: @"%f", self.mediaContent.currentTime]
                                 });
        }
    }
}

@end
