#import "RNGADMediaView.h"
#import "RNNativeAdMobUtils.h"

@implementation RNGADMediaView : GADMediaView

GADVideoController *vc;

- (void)setPause:(BOOL *)pause {
    if (pause) {
        
        if (self.mediaContent != nil && vc != nil) {
            [vc pause];
        }
    } else {
        if (self.mediaContent != nil && vc != nil) {
            [vc play];
        }
    }
}



- (void)setMuted:(BOOL *)muted {
    
    if (self.mediaContent != nil && vc != nil) {
        [vc setMute:muted? YES : NO];
    }
}

- (void)videoControllerDidPlayVideo:(GADVideoController *)videoController {
    if (self.onVideoPlay) {
        vc = videoController;
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
