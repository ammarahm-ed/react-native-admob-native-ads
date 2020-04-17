#import "RNGADNativeView.h"
#import "RNAdMobUtils.h"
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

@import GoogleMobileAds;

@implementation RNGADNativeView : GADUnifiedNativeAdView

RCTBridge *bridge;


NSString *adUnitId;
NSNumber *refreshingInterval;

- (instancetype)initWithBridge:(RCTBridge *)_bridge
{
    refreshingInterval = @60000;
  if (self = [super init]) {
    bridge = _bridge;
  }
  return self;
}








- (void)setTestDevices:(NSArray *)testDevices
{
    _testDevices = RNAdMobProcessTestDevices(testDevices, kDFPSimulatorID);
}

- (void)setRefreshInterval:(NSNumber *)refreshInterval
{
    refreshingInterval = refreshInterval;
}

- (void)setAdUnitID:(NSString *)adUnitID
{
    adUnitId = adUnitID;
    [self loadAd:adUnitId];
}

- (void)setHeadline:(NSNumber *)headline {
    
    dispatch_async(RCTGetUIManagerQueue(),^{
          
          [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {

              UIView *headlineView = viewRegistry[headline];
                           if (headlineView != nil) {
                                               [self setHeadlineView:headlineView];
                                           }
            }];
      });
}

- (void)setIcon:(NSNumber *)icon {
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {

            UIView *iconView = viewRegistry[icon];
                         if (iconView != nil) {
                                             [self setIconView:iconView];
                                         }
          }];
    });
    
  
}

- (void)setImage:(NSNumber *)image {
    
    dispatch_async(RCTGetUIManagerQueue(),^{
                     
                     [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {

                         UIView *imageView = viewRegistry[image];
                                      if (imageView != nil) {
                                                          [self setImageView:imageView];
                                                      }
                       }];
                 });

}

- (void)setMediaview:(NSNumber *)mediaview
{
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {

            GADMediaView *mediaView = (GADMediaView *) viewRegistry[mediaview];
                         if (mediaView != nil) {
                                             [self setMediaView:mediaView];
                                         }
          }];
    });
}

- (void)setTagline:(NSNumber *)tagline
{
    
    dispatch_async(RCTGetUIManagerQueue(),^{
                  
                  [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {

                      UIView *taglineView = viewRegistry[tagline];
                                   if (taglineView != nil) {
                                                       [self setPriceView:taglineView];
                                                   }
                    }];
              });
   
}

- (void)setAdvertiser:(NSNumber *)advertiser
{
    dispatch_async(RCTGetUIManagerQueue(),^{
                
                [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {

                    UIView *advertiserView = viewRegistry[advertiser];
                                 if (advertiserView != nil) {
                                                     [self setPriceView:advertiserView];
                                                 }
                  }];
            });
    
}
- (void)setPrice:(NSNumber *)price
{
    
    dispatch_async(RCTGetUIManagerQueue(),^{
             
             [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {

                 UIView *priceView = viewRegistry[price];
                              if (priceView != nil) {
                                                  [self setPriceView:priceView];
                                              }
               }];
         });
    
    
   
    
    
}

- (void)setStore:(NSNumber *)store
{
    
    dispatch_async(RCTGetUIManagerQueue(),^{
           
           [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {

               UIView *storeView = viewRegistry[store];
                            if (storeView != nil) {
                                                [self setStoreView:storeView];
                                            }
             }];
       });
    
    
    
    
}

- (void)setStarrating:(NSNumber *)starrating
{
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {

            UIView *starratingView = viewRegistry[starrating];
                          if (starratingView != nil) {
                              [self setStarRatingView:starratingView];
                          }
          }];
    });
 
}

- (void)setCallToAction:(NSNumber *)callToAction
{
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
             UIView *callToActionView = viewRegistry[callToAction];
            
             if (callToActionView != nil){
                [self setCallToActionView:callToActionView];
             }
            
          }];
    });

}



- (void)loadAd:(NSString *)adUnitId
{
    UIWindow *keyWindow = [[UIApplication sharedApplication] keyWindow];
    UIViewController *rootViewController = [keyWindow rootViewController];
    
    GADNativeAdViewAdOptions *adViewOptions = [GADNativeAdViewAdOptions new];
    
    adViewOptions.preferredAdChoicesPosition = GADAdChoicesPositionTopRightCorner;
    
    
    self.adLoader = [[GADAdLoader alloc]
                     initWithAdUnitID:adUnitId
                     rootViewController:rootViewController
                     adTypes:@[ kGADAdLoaderAdTypeUnifiedNative ]
                     options:@[adViewOptions]];
    
    self.adLoader.delegate = self;
    GADRequest *request = [GADRequest request];
    GADMobileAds.sharedInstance.requestConfiguration.testDeviceIdentifiers = _testDevices;
    [self.adLoader loadRequest:request];
}


- (void)adLoader:(GADAdLoader *)adLoader didFailToReceiveAdWithError:(GADRequestError *)error {
    if (self.onAdFailedToLoad) {
        self.onAdFailedToLoad(@{ @"error": @{ @"message": [error localizedDescription] } });
    }
    
}


- (void)adLoader:(GADAdLoader *)adLoader didReceiveUnifiedNativeAd:(GADUnifiedNativeAd *)nativeAd {
    
    if (self.onAdLoaded) {
        self.onAdLoaded(@{});
    }

    nativeAd.delegate = self;
    [self setNativeAd:nativeAd];
    
    if (nativeAd != NULL) {
        NSMutableDictionary *dic = [NSMutableDictionary dictionary];
        
        [dic setValue:nativeAd.headline forKey:@"headline"];
        [dic setValue:nativeAd.body forKey:@"tagline"];
        [dic setValue:nativeAd.advertiser forKey:@"advertiser"];
        [dic setValue:nativeAd.store forKey:@"store"];
        [dic setValue:nativeAd.price forKey:@"price"];
        [dic setValue:nativeAd.callToAction forKey:@"callToAction"];
        
     NSMutableArray *array = [NSMutableArray array];
        
         GADNativeAdImage *image = [nativeAd.images objectAtIndex:0];
         NSString *url = [image.imageURL absoluteString];
          [array addObject:url];
            
        [dic setObject:array forKey:@"images"];
       
        
        [dic setValue:[nativeAd.icon.imageURL absoluteString] forKey:@"icon"];
        [dic setValue:nativeAd.starRating forKey:@"rating"];
        
        self.onUnifiedNativeAdLoaded(dic);
    }
    

    double delayInSeconds = refreshingInterval.intValue/1000;
    dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delayInSeconds * NSEC_PER_SEC));
    dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
        [self loadAd:adUnitId];
    });
    
    
    
   

}


- (void)nativeAdDidRecordImpression:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if (self.onAdImpression) {
        self.onAdImpression(@{});
    
    }
}

- (void)nativeAdDidRecordClick:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if (self.onAdClicked) {
        self.onAdClicked(@{});
    }
}

- (void)nativeAdWillPresentScreen:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if (self.onAdOpened) {
        self.onAdOpened(@{});
    }
}

- (void)nativeAdWillDismissScreen:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if (self.onAdClosed) {
        self.onAdClosed(@{});
    }
}

- (void)nativeAdWillLeaveApplication:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if(self.onAdLeftApplication) {
        self.onAdLeftApplication(@{});
    }
}



@end




