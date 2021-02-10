#import "RCTViewManager.h"

#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>
#import <React/RCTEventDispatcher.h>
#else
#import "RCTBridge.h"
#import "RCTUIManager.h"
#import "RCTEventDispatcher.h"
#endif

#import "RNGADMediaViewManager.h"
#import "RNGADMediaView.h"

@import GoogleMobileAds;

@implementation RNGADMediaViewManager: RCTViewManager

RCT_EXPORT_MODULE(RNGADMediaView);

-(UIView *)view
{

 return [[RNGADMediaView alloc] init];
    
}

RCT_EXPORT_VIEW_PROPERTY(pause, BOOL)
RCT_EXPORT_VIEW_PROPERTY(muted, BOOL)

RCT_EXPORT_VIEW_PROPERTY(onVideoPlay, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoPause, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoEnd, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoMute, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoStart, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onVideoProgress, RCTBubblingEventBlock)



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
