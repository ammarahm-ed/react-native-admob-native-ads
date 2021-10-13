#import "RNGADNativeViewManager.h"
#import "RNGADNativeView.h"


@implementation RNGADNativeViewManager

+ (NSString *)  EVENT_AD_FAILED_TO_LOAD {return @"onAdFailedToLoad";}
+ (NSString *)  EVENT_AD_CLICKED{return @"onAdClicked";}
+ (NSString *)  EVENT_AD_CLOSED{return @"onAdClosed";}
+ (NSString *)  EVENT_AD_OPENED{return @"onAdOpened";}
+ (NSString *)  EVENT_AD_IMPRESSION {return @"onAdImpression";}
+ (NSString *)  EVENT_AD_LOADED{return @"onAdLoaded";}
+ (NSString *)  EVENT_AD_LEFT_APPLICATION {return @"onAdLeftApplication";}
+ (NSString *)  EVENT_UNIFIED_NATIVE_AD_LOADED {return@ "onUnifiedNativeAdLoaded";}

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
RCT_EXPORT_VIEW_PROPERTY(repository, NSString)

RCT_EXPORT_VIEW_PROPERTY(onSizeChange, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAppEvent, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdLoaded, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdFailedToLoad, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdOpened, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdClosed, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdLeftApplication, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdClicked, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onAdImpression, RCTDirectEventBlock)
RCT_EXPORT_VIEW_PROPERTY(onNativeAdLoaded, RCTDirectEventBlock)



@end
