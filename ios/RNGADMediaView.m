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

GADVideoController *videoController;
GADNativeAd *nativeAd;

- (void)setPause:(BOOL *)pause {
    if (pause) {
        
        if (videoController) {
            [videoController pause];
        }
    } else {
        if (videoController) {
            [videoController play];
        }
    }
}

- (void)setMuted:(BOOL *)muted {
    if (videoController) {
        [videoController setMute:muted? true : false];
       
    }

}



- (void)setVideoController:( GADVideoController * _Nullable)vc {
    if (vc != nil) {
        videoController = vc;
    }
    
}

- (void)setNativeAd:(GADNativeAd * _Nullable)ad {
    if (ad != nil) {
        nativeAd = ad;
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
        if (nativeAd != nil && nativeAd.mediaContent.hasVideoContent && nativeAd.mediaContent.duration > 0  ) {
            
            self.onVideoProgress(@{
                @"duration":[NSString stringWithFormat: @"%f", nativeAd.mediaContent.duration],
                @"currentTime":[NSString stringWithFormat: @"%f", nativeAd.mediaContent.currentTime]
         });
        }
    
    }
}

@end
