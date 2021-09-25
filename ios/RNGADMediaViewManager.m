#import "RNGADMediaViewManager.h"
#import "RNGADMediaView.h"

@implementation RNGADMediaViewManager: RCTViewManager

RCT_EXPORT_MODULE(RNGADMediaView);

-(UIView *)view
{

 return [[RNGADMediaView alloc] init];
    
}

RCT_EXPORT_VIEW_PROPERTY(pause, BOOL)
RCT_EXPORT_VIEW_PROPERTY(muted, BOOL)

RCT_EXPORT_VIEW_PROPERTY(onVideoPlay, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoPause, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoEnd, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoMute, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoStart, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoProgress, RCTDirectEventBlock)



RCT_EXPORT_METHOD(getProgress:(nonnull NSNumber *)reactTag)
{
  [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RNGADMediaView *> *viewRegistry) {
      RNGADMediaView *view = viewRegistry[reactTag];
    if (![view isKindOfClass:[RNGADMediaView class]]) {
      RCTLogError(@"Invalid view returned from registry, expecting RNGADNativeView, got: %@", view);
    } else {
      [view getCurrentProgress];
    }
  }];
}


@end
