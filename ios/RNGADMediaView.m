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

@implementation RNGADMediaView

- (void)setPause:(BOOL *)pause {
    if (pause) {
        if (self.videoController) {
            [self.videoController pause];
        }
    } else {
        if (self.videoController) {
            [self.videoController play];
        }
    }
}

- (void)setMuted:(BOOL *)muted {
    if (self.videoController) {
        [self.videoController setMute:muted? TRUE : FALSE];
    }

}


- (void)setVideoController:(GADVideoController *)videoController {
    self.videoController = videoController;
}

- (void)setNativeAd:(GADUnifiedNativeAd *)nativeAd {
    self.nativeAd = nativeAd;
}

- (void)videoControllerDidPlayVideo:(GADVideoController *)videoController {
    if (self.onVideoPlay) {
        self.onVideoPlay(nil);
    }
}

- (void)videoControllerDidPauseVideo:(GADVideoController *)videoController {
    if (self.onVideoPause) {
        self.onVideoPause(nil);
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
        self.onVideoEnd(nil);
    }
}

- (void)getCurrentProgress
{
    if (self.onVideoProgress) {
        if (self.nativeAd != nil && self.nativeAd.mediaContent.hasVideoContent && self.nativeAd.mediaContent.duration > 0  ) {
            self.onVideoProgress(@{
                @"duration":[NSString stringWithFormat: @"%f", self.nativeAd.mediaContent.duration],
                @"currentTime":[NSString stringWithFormat: @"%f", self.nativeAd.mediaContent.currentTime]
         });
        }
    
    }
}

@end
