#import "RNGADMediaView.h"
#import "RNNativeAdMobUtils.h"

@implementation RNGADMediaView : UIView

GADMediaView *cMediaView;
GADVideoController *vc;


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
        
        if (cMediaView.mediaContent != nil && vc != nil) {
            [vc pause];
        }
    } else {
        if (cMediaView.mediaContent != nil && vc != nil) {
            [vc play];
        }
    }
}



- (void)setMuted:(BOOL *)muted {
    if (cMediaView.mediaContent != nil && vc != nil) {
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
        if (cMediaView.mediaContent != nil && cMediaView.mediaContent.hasVideoContent && cMediaView.mediaContent.duration > 0  ) {
            self.onVideoProgress(@{
                @"duration":[NSString stringWithFormat: @"%f", cMediaView.mediaContent.duration],
                @"currentTime":[NSString stringWithFormat: @"%f", cMediaView.mediaContent.currentTime]
                                 });
        }
    }
}

@end
