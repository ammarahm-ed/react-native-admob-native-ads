#import "RNGADNativeView.h"
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
#import <React/RCTUIManagerUtils.h>
#import <React/RCTImageView.h>
#import "RNGADMediaView.h"

@import GoogleMobileAds;
@import FacebookAdapter;

@implementation RNGADNativeView : GADNativeAdView

RCTBridge *bridge;


NSString *adUnitId;
NSNumber *refreshingInterval;
NSNumber *delay;
NSNumber *adChoicesPlace;

NSNumber *mediaViewId;
NSNumber *iconViewId;
NSNumber *imageViewId;
NSNumber *callToActionViewId;
NSNumber *headlineViewId;
NSNumber *priceViewId;
NSNumber *starViewId;
NSNumber *advertiserViewId;
NSNumber *taglineViewId;
NSNumber *storeViewId;

dispatch_block_t block;
RNGADMediaView *rnMediaView;
BOOL isLoading = FALSE;

GADNativeAdViewAdOptions *adPlacementOptions;
GADNativeAdMediaAdLoaderOptions *adMediaOptions;

GAMRequest *adRequest;
GADVideoOptions *adVideoOptions;

BOOL *nonPersonalizedAds;



- (instancetype)initWithBridge:(RCTBridge *)_bridge
{
    delay = @1;
    refreshingInterval = @60000;
    
    adRequest = [GAMRequest request];
    adPlacementOptions = [[GADNativeAdViewAdOptions alloc] init];
    adVideoOptions = [[GADVideoOptions alloc] init];
    adMediaOptions = [[GADNativeAdMediaAdLoaderOptions alloc] init];
    
    if (self = [super init]) {
        bridge = _bridge;
    }
    return self;
}


- (void)setMediationOptions:(NSDictionary *)mediationOptions {
    NSArray *allKeys = [mediationOptions allKeys];
    if ([allKeys containsObject:@"nativeBanner"]) {
        
        /**
         The following code adds support for Native Banner for Facebook Mediation Ads.
         */
        
        GADFBNetworkExtras * extras = [[GADFBNetworkExtras alloc] init];
        
        if ([[mediationOptions valueForKey:@"nativeBanner"] isEqual:[NSNumber numberWithBool:NO]]) {
            extras.nativeAdFormat = GADFBAdFormatNative;
        } else {
            extras.nativeAdFormat = GADFBAdFormatNativeBanner;
        }
       
        
        [adRequest registerAdNetworkExtras:extras];
    }
    
}

- (void)setTargetingOptions:(NSDictionary *)targetingOptions {
    NSArray *allKeys = [targetingOptions allKeys];
    
    if ([allKeys containsObject:@"targets"]) {
        [adRequest setCustomTargeting:(NSDictionary *) [targetingOptions objectForKey:@"targets"]];
    }
    
    if ([allKeys containsObject:@"categoryExclusions"]) {
        [adRequest setCategoryExclusions:(NSArray *) [targetingOptions objectForKey:@"categoryExclusions"]];
    }
    
    if ([allKeys containsObject:@"publisherId"]) {
        [adRequest setPublisherProvidedID:(NSString *) [targetingOptions objectForKey:@"publisherId"]];
    }
    if ([allKeys containsObject:@"keywords"]) {
        [adRequest setKeywords:(NSArray *) [targetingOptions objectForKey:@"keywords"]];
    }
    
    if ([allKeys containsObject:@"contentUrl"]) {
        [adRequest setContentURL:(NSString *) [targetingOptions objectForKey:@"contentUrl"]];
    }
    
    if ([allKeys containsObject:@"neighboringContentUrls"]) {
        // Do Nothing
    }
    
}

- (void)setVideoOptions:(NSDictionary *)videoOptions {
    
    NSArray *allKeys = [videoOptions allKeys];
    
    if ([allKeys containsObject:@"muted"]) {
        [adVideoOptions setStartMuted:(BOOL)[videoOptions valueForKey:@"muted"]];
    }
    
    if ([allKeys containsObject:@"clickToExpand"]) {
        [adVideoOptions setClickToExpandRequested:(BOOL)[videoOptions valueForKey:@"clickToExpand"]];
    }
    
    if ([allKeys containsObject:@"customControlsRequested"]) {
        [adVideoOptions setCustomControlsRequested:(BOOL)[videoOptions valueForKey:@"customControlsRequested"]];
    }
    
}


- (void)setMediaAspectRatio:(NSNumber *)mediaAspectRatio {
    
    if ([mediaAspectRatio isEqualToNumber:@0]) {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioUnknown];
    } else if ([mediaAspectRatio isEqualToNumber:@1]) {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioAny];
    }  else if ([mediaAspectRatio isEqualToNumber:@2]) {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioLandscape];
    }  else if ([mediaAspectRatio isEqualToNumber:@3]) {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioPortrait];
    } else {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioSquare];
    }
    
}

- (void)setAdChoicesPlacement:(NSNumber *)adChoicesPlacement {
    
    adChoicesPlace = adChoicesPlacement;
    
    if ([adChoicesPlace isEqualToNumber:@0]) {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionTopLeftCorner];
    } else if ([adChoicesPlace isEqualToNumber:@1]) {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionTopRightCorner];
    }  else if ([adChoicesPlace isEqualToNumber:@2]) {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionBottomRightCorner];
    }  else if ([adChoicesPlace isEqualToNumber:@3]) {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionBottomLeftCorner];
    } else {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionTopRightCorner];
    }
    
}

- (void)setRequestNonPersonalizedAdsOnly:(BOOL *)requestNonPersonalizedAdsOnly {
    
    nonPersonalizedAds = requestNonPersonalizedAdsOnly;
    GADExtras *extras = [[GADExtras alloc] init];
    
    if (nonPersonalizedAds) {
        extras.additionalParameters = @{@"npa": @"1"};
    } else {
        extras.additionalParameters = @{@"npa": @"0"};
    }
    [adRequest registerAdNetworkExtras:extras];
    
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
}

- (void) reloadAdInView:(GADNativeAd *)nativeAd isMedia:(BOOL )media {
    if (block != nil) {
        dispatch_block_cancel(block);
    }
    
    block = dispatch_block_create(DISPATCH_BLOCK_NO_QOS_CLASS, ^{
        if (nativeAd != nil) {
            [self setNativeAd:nativeAd];
            
            if (nativeAd != nil &&  nativeAd.icon.image != nil) {
                UIImageView *imageV = (UIImageView *) self.iconView;
                [imageV setImage:nativeAd.icon.image];
            }
            
        }
    });
    dispatch_time_t time = dispatch_time(DISPATCH_TIME_NOW, 1 * NSEC_PER_SEC);
    dispatch_after(time, dispatch_get_main_queue(),block);
    
    
}

- (void)setHeadline:(NSNumber *)headline {
    
   headlineViewId = headline;
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
            UIView *headlineView = viewRegistry[headline];
        
            if (headlineView != nil) {
                [self setHeadlineView:headlineView];
                if (self.nativeAd != nil) {
                    [self reloadAdInView:self.nativeAd isMedia:NO];
                }
                
            }
        }];
    });
}

- (void)setIcon:(NSNumber *)icon {
    
    iconViewId = icon;
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
            UIImageView *iconView = (UIImageView *) viewRegistry[icon];
            if (iconView != nil) {
                [self setIconView:iconView];
                if (self.nativeAd != nil) {
                    [self reloadAdInView:self.nativeAd isMedia:NO];
                }
                
            }
        }];
    });
    
    
}

- (void)setImage:(NSNumber *)image {
    
    imageViewId = image;
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
            UIImageView *imageView = (UIImageView *) viewRegistry[image];
            if (imageView != nil) {
                [self setImageView:imageView];
                if (self.nativeAd != nil) {
                    [self reloadAdInView:self.nativeAd isMedia:NO];
                }
            }
        }];
    });
    
}

- (void)setMediaview:(NSNumber *)mediaview
{
    mediaViewId = mediaview;
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            rnMediaView = (RNGADMediaView *) viewRegistry[mediaview];
            
            if (rnMediaView != nil) {
                [self setMediaView:(GADMediaView *) rnMediaView.subviews.firstObject];
                if (self.nativeAd != nil) {
                    [self.mediaView setMediaContent:self.nativeAd.mediaContent];
                    [self reloadAdInView:self.nativeAd isMedia:YES];
                }
                if (self.nativeAd.mediaContent.videoController != nil) {
                    self.nativeAd.mediaContent.videoController.delegate = rnMediaView.self;
                    
                }
            }
        }];
    });
}

- (void)setTagline:(NSNumber *)tagline
{
    
    taglineViewId = tagline;
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
            UIView *taglineView = viewRegistry[tagline];
            if (taglineView != nil) {
                [self setPriceView:taglineView];
                if (self.nativeAd != nil) {
                    [self reloadAdInView:self.nativeAd isMedia:NO];
                }
            }
        }];
    });
    
}

- (void)setAdvertiser:(NSNumber *)advertiser
{
    
    
    advertiserViewId = advertiser;
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
            UIView *advertiserView = viewRegistry[advertiser];
            if (advertiserView != nil) {
                [self setAdvertiserView:advertiserView];
                if (self.nativeAd != nil) {
                    [self reloadAdInView:self.nativeAd isMedia:NO];
                }
            }
        }];
    });
    
}
- (void)setPrice:(NSNumber *)price
{
    
    priceViewId = price;
    
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
            UIView *priceView = viewRegistry[price];
            if (priceView != nil) {
                [self setPriceView:priceView];
                if (self.nativeAd != nil) {
                    [self reloadAdInView:self.nativeAd isMedia:NO];
                }
            }
        }];
    });
    
}

- (void)setStore:(NSNumber *)store
{
    
    storeViewId = store;
    
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
            UIView *storeView = viewRegistry[store];
            if (storeView != nil) {
                [self setStoreView:storeView];
                if (self.nativeAd != nil) {
                    [self reloadAdInView:self.nativeAd isMedia:NO];
                }
            }
        }];
    });
}

- (void)setStarrating:(NSNumber *)starrating
{
    
    starViewId = starrating;
    
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
            UIView *starratingView = viewRegistry[starrating];
            if (starratingView != nil) {
                [self setStarRatingView:starratingView];
                if (self.nativeAd != nil) {
                    
                    [self reloadAdInView:self.nativeAd isMedia:NO];
                }
            }
        }];
    });
    
}

- (void)setCallToAction:(NSNumber *)callToAction
{
    callToActionViewId = callToAction;
    
    dispatch_async(RCTGetUIManagerQueue(),^{
        
        [bridge.uiManager addUIBlock:^(RCTUIManager *uiManager, NSDictionary<NSNumber *,UIView *> *viewRegistry) {
            
            UIView *cAv = viewRegistry[callToAction];
            if (cAv != nil){
                [self setCallToActionView:cAv];
                if (self.nativeAd != nil) {
                    [self reloadAdInView:self.nativeAd isMedia:NO];
                }
            }
            
        }];
    });
    
}



- (void)loadAd
{
    if (isLoading == TRUE) return;
    isLoading = TRUE;
    
    self.adLoader = [[GADAdLoader alloc] initWithAdUnitID:adUnitId
                                       rootViewController:self.reactViewController
                                                  adTypes:@[ kGADAdLoaderAdTypeNative ]
                                                  options:@[adMediaOptions,adPlacementOptions,adVideoOptions]];
    
    self.adLoader.delegate = self;
    
    [self.adLoader loadRequest:adRequest];
    
}



- (void)adLoader:(GADAdLoader *)adLoader didFailToReceiveAdWithError:(NSError *)error {
    isLoading = FALSE;
    if (self.onAdFailedToLoad) {
        self.onAdFailedToLoad(@{ @"error": @{ @"message": [error localizedDescription] } });
    }
}

- (void)adLoaderDidFinishLoading:(GADAdLoader *)adLoader {
    isLoading = FALSE;
}



- (void)adLoader:(GADAdLoader *)adLoader didReceiveNativeAd:(GADNativeAd *)nativeAd {
    isLoading = FALSE;
    if (self.onAdLoaded) {
        self.onAdLoaded(@{});
        
    }
    
    nativeAd.delegate = self;
    
    if (rnMediaView != nil) {
        [self setMediaView:rnMediaView.subviews.firstObject];
        if (nativeAd.mediaContent.videoController != nil) {
            nativeAd.mediaContent.videoController.delegate = rnMediaView.self;
        }
    }
    
    [self setNativeAd:nativeAd];
    
    if (self.mediaView != nil) {
        [self.mediaView setMediaContent:nativeAd.mediaContent];
    }
    
    if (nativeAd != NULL) {
        NSMutableDictionary *dic = [NSMutableDictionary dictionary];
        
        [dic setValue:nativeAd.headline forKey:@"headline"];
        [dic setValue:nativeAd.body forKey:@"tagline"];
        [dic setValue:nativeAd.advertiser forKey:@"advertiser"];
        
        [dic setValue:nativeAd.callToAction forKey:@"callToAction"];
        
        
        if (nativeAd.store != nil) {
            [dic setValue:nativeAd.store forKey:@"store"];
        }
        
        if (nativeAd.price != nil) {
            [dic setValue:nativeAd.price forKey:@"price"];
        }
        
        if (nativeAd.starRating != nil) {
            [dic setValue:nativeAd.starRating forKey:@"rating"];
        }
        
        if (nativeAd.mediaContent.hasVideoContent) {
            [dic setValue:@YES forKey:@"video"];
        } else {
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
            if (nativeAd.icon.imageURL != nil) {
                
                UIImageView *imageV = (UIImageView *) self.iconView;
                [imageV setImage:nativeAd.icon.image];
                
                [dic setValue:[nativeAd.icon.imageURL absoluteString] forKey:@"icon"];
            } else {
                if (nativeAd.icon.image != nil) {
                    UIImageView *imageV = (UIImageView *) self.iconView;
                    [imageV setImage:nativeAd.icon.image];
                }
                [dic setValue:@"empty" forKey:@"icon"];
            }
            
        } else {
            [dic setValue:@"noicon" forKey:@"icon"];
            
        }
        
        self.onUnifiedNativeAdLoaded(dic);
        
    }
    
}


- (void)nativeAdDidRecordImpression:(nonnull GADNativeAd *)nativeAd
{
    if (self.onAdImpression) {
        self.onAdImpression(@{});
        
    }
}

- (void)nativeAdDidRecordClick:(nonnull GADNativeAd *)nativeAd
{
    if (self.onAdClicked) {
        self.onAdClicked(@{});
    }
}

- (void)nativeAdWillPresentScreen:(nonnull GADNativeAd *)nativeAd
{
    if (self.onAdOpened) {
        self.onAdOpened(@{});
    }
}

- (void)nativeAdWillDismissScreen:(nonnull GADNativeAd *)nativeAd
{
    if (self.onAdClosed) {
        self.onAdClosed(@{});
    }
}

- (void)nativeAdWillLeaveApplication:(nonnull GADNativeAd *)nativeAd
{
    if(self.onAdLeftApplication) {
        self.onAdLeftApplication(@{});
    }
}

@end




