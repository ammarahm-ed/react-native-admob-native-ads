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
NSNumber *delay;
NSNumber *adChoicesPlace;
BOOL *nonPersonalizedAds;



- (instancetype)initWithBridge:(RCTBridge *)_bridge
{
    delay = @1;
    refreshingInterval = @60000;
    if (self = [super init]) {
        bridge = _bridge;
    }
    return self;
}

- (void)setAdChoicesPlacement:(NSNumber *)adChoicesPlacement {
    
    adChoicesPlace = adChoicesPlacement;
}

- (void)setRequestNonPersonalizedAdsOnly:(BOOL *)requestNonPersonalizedAdsOnly {
    
    nonPersonalizedAds = requestNonPersonalizedAdsOnly;
}

- (void)setDelayAdLoad:(NSNumber *)delayAdLoad
{
   
}

- (void)setTestDevices:(NSArray *)testDevices
{
   // _testDevices = RNAdMobProcessTestDevices(testDevices, kDFPSimulatorID);
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
                headlineView.userInteractionEnabled = NO;
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
                iconView.userInteractionEnabled = NO;
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
                imageView.userInteractionEnabled = NO;
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
                taglineView.userInteractionEnabled = NO;
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
                advertiserView.userInteractionEnabled = NO;
                [self setAdvertiserView:advertiserView];
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
                priceView.userInteractionEnabled = NO;
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
                storeView.userInteractionEnabled = NO;
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
                starratingView.userInteractionEnabled = NO;
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
    
    
    if ([adChoicesPlace isEqualToNumber:@0]) {
        [adViewOptions setPreferredAdChoicesPosition:GADAdChoicesPositionTopLeftCorner];
    } else if ([adChoicesPlace isEqualToNumber:@1]) {
        [adViewOptions setPreferredAdChoicesPosition:GADAdChoicesPositionTopRightCorner];
    }  else if ([adChoicesPlace isEqualToNumber:@2]) {
        [adViewOptions setPreferredAdChoicesPosition:GADAdChoicesPositionBottomRightCorner];
    }  else if ([adChoicesPlace isEqualToNumber:@3]) {
        [adViewOptions setPreferredAdChoicesPosition:GADAdChoicesPositionBottomLeftCorner];
    } else {
        [adViewOptions setPreferredAdChoicesPosition:GADAdChoicesPositionTopRightCorner];
       
    }
    
    
    
    
    
    self.adLoader = [[GADAdLoader alloc]
                     initWithAdUnitID:adUnitId
                     rootViewController:rootViewController
                     adTypes:@[ kGADAdLoaderAdTypeUnifiedNative ]
                     options:@[adViewOptions]];
    
    
    self.adLoader.delegate = self;
    GADRequest *request = [GADRequest request];
    
    if (nonPersonalizedAds) {
        GADExtras *extras = [[GADExtras alloc] init];
        extras.additionalParameters = @{@"npa": @"1"};
        [request registerAdNetworkExtras:extras];
    }
    
    //GADMobileAds.sharedInstance.requestConfiguration.testDeviceIdentifiers = _testDevices;
    [self.adLoader loadRequest:request];
}


- (void)adLoader:(GADAdLoader *)adLoader didFailToReceiveAdWithError:(GADRequestError *)error {
    if (self.onAdFailedToLoad) {
        self.onAdFailedToLoad(@{ @"error": @{ @"message": [error localizedDescription] } });
        
         double delayInSeconds = refreshingInterval.intValue/1000;
               dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delayInSeconds * NSEC_PER_SEC));
               dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
                   [self loadAd:adUnitId];
         });
        
    }
    
}


- (void)adLoader:(GADAdLoader *)adLoader didReceiveUnifiedNativeAd:(GADUnifiedNativeAd *)nativeAd {
    dispatch_after((int64_t)((delay.intValue/1000) * NSEC_PER_SEC), dispatch_get_main_queue(), ^(void){
        
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
            
            if (nativeAd.mediaContent.hasVideoContent) {
                [dic setValue:@YES forKey:@"video"];
            }else {
                [dic setValue:@NO forKey:@"video"];
            }
            NSString *aspectRatio = @(nativeAd.mediaContent.aspectRatio).stringValue;
            
            [dic setValue:aspectRatio forKey:@"aspectRatio"];
            NSMutableArray *images = [NSMutableArray array];
            
            for (int i=0;i < nativeAd.images.count;i++) {
                NSMutableDictionary *imageDic = [NSMutableDictionary dictionary];
                GADNativeAdImage *image = [nativeAd.images objectAtIndex:i];
                NSString *url = [image.imageURL absoluteString];
                [imageDic setValue:url forKey:@"url"];
                NSInteger val = @(image.image.size.width).integerValue;
                [imageDic setValue: [NSNumber numberWithInteger:val]  forKey:@"width"];
                NSInteger val2 = @(image.image.size.height).integerValue;
                [imageDic setValue: [NSNumber numberWithInteger:val2] forKey:@"height"];
                [images addObject:imageDic];
                
            }
            
            
            
            [dic setObject:images forKey:@"images"];
            if (nativeAd.icon != nil) {
                     [dic setValue:[nativeAd.icon.imageURL absoluteString] forKey:@"icon"];
            } else {
                
                NSString * someString = nativeAd.responseInfo.adNetworkClassName;
                NSLog(@"%@", someString);
                
                if ([nativeAd.responseInfo.adNetworkClassName isEqualToString:@"GADMAdapterGoogleAdMobAds"]) {
                             [dic setValue:@"noicon" forKey:@"icon"];
                } else {
                      [dic setValue:@"empty" forKey:@"icon"];
                }
            
            
            }
           
            
            [dic setValue:nativeAd.starRating forKey:@"rating"];
            
            self.onUnifiedNativeAdLoaded(dic);
        }
        
        
        double delayInSeconds = refreshingInterval.intValue/1000;
        dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, (int64_t)(delayInSeconds * NSEC_PER_SEC));
        dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
            [self loadAd:adUnitId];
        });
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




