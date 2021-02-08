#import "RNGADNativeViewManager.h"
#import "RNGADNativeView.h"

#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>
#import <React/RCTEventDispatcher.h>
#import <React/RCTViewManager.h>
#else
#import "RCTBridge.h"
#import "RCTUIManager.h"
#import "RCTViewManager.h"
#import "RCTEventDispatcher.h"
#endif


@implementation RNGADNativeViewManager 

RCT_EXPORT_MODULE(RNGADNativeView);

-(UIView *)view
{
    return [[RNGADNativeView alloc]initWithBridge:self.bridge];
}



RCT_EXPORT_METHOD(loadAd:(nonnull NSNumber *)reactTag)
{
  [self.bridge.uiManager addUIBlock:^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RNGADNativeView *> *viewRegistry) {
      RNGADNativeView *view = viewRegistry[reactTag];
    if (![view isKindOfClass:[RNGADNativeView class]]) {
      RCTLogError(@"Invalid view returned from registry, expecting RNGADNativeView, got: %@", view);
    } else {
      [view loadAd];
    }
  }];
}


RCT_EXPORT_VIEW_PROPERTY(adSize, NSString)
RCT_EXPORT_VIEW_PROPERTY(testDevices, NSArray)

RCT_EXPORT_VIEW_PROPERTY(targetingOptions, NSDictionary)
RCT_EXPORT_VIEW_PROPERTY(mediationOptions, NSDictionary)
RCT_EXPORT_VIEW_PROPERTY(videoOptions, NSDictionary)
RCT_EXPORT_VIEW_PROPERTY(mediaAspectRatio, NSNumber)

RCT_EXPORT_VIEW_PROPERTY(refreshInterval, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(adUnitID, NSString)
RCT_EXPORT_VIEW_PROPERTY(delayAdLoad, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(headline, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(tagline, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(advertiser, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(store, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(icon, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(image, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(mediaview, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(price, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(starrating, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(callToAction, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(requestNonPersonalizedAdsOnly, BOOL)
RCT_EXPORT_VIEW_PROPERTY(adChoicesPlacement, NSNumber)

RCT_EXPORT_VIEW_PROPERTY(onSizeChange, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAppEvent, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdLoaded, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdFailedToLoad, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdOpened, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdClosed, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdLeftApplication, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdClicked, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdImpression, RCTBubblingEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onUnifiedNativeAdLoaded, RCTBubblingEventBlock)
@end
