//
//  RNAdMobUnifiedAdQueueWrapper.m
//  react-native-admob-native-ads
//
//  Created by Ali on 8/25/21.
//

#import <Foundation/Foundation.h>
#import "RNAdMobUnifiedAdQueueWrapper.h"
#import "UnifiedNativeAdLoadedListener.h"
#import "RNAdMobUnifiedAdContainer.h"
#import "EventEmitter.h"
#import "CacheManager.h"
@import GoogleMobileAds;
#ifdef MEDIATION_FACEBOOK
@import FacebookAdapter;
#endif

@implementation RNAdMobUnifiedAdQueueWrapper{
    GADAdLoader* adLoader;
    GAMRequest* adRequest;

    NSMutableSet<id<AdListener>>* attachedAdListeners;
    UnifiedNativeAdLoadedListener* unifiedNativeAdLoadedListener;
    GADVideoOptions* adVideoOptions;
    GADNativeAdMediaAdLoaderOptions* adMediaOptions;
    GADNativeAdViewAdOptions* adPlacementOptions;
    NSDictionary* targetingOptions;
    int loadingAdRequestCount;
    int retryDelay;
    int totalRetryCount;
    int retryCount;
}

-(instancetype)initWithConfig:(NSDictionary *)config repo:(NSString *)repo{
    if (self = [super init])  {
        self.totalAds = 5;
        self.expirationInterval = 3600000; // in ms
        self.isMediationEnabled = false;
        retryDelay = 3000;// in ms
        totalRetryCount = 10;
        retryCount = 0;
        adRequest = [GAMRequest request];
        loadingAdRequestCount = 0;
        adVideoOptions = [[GADVideoOptions alloc]init];
        adMediaOptions = [[GADNativeAdMediaAdLoaderOptions alloc] init];
        adPlacementOptions = [[GADNativeAdViewAdOptions alloc]init];

        attachedAdListeners = [[NSMutableSet<id<AdListener>> alloc] init];
    }

    //Set repository settings
    _adUnitId = [config objectForKey:@"adUnitId"] ;
    _name = repo;
    if ([config objectForKey:@"numOfAds"]){
        _totalAds = ((NSNumber *)[config objectForKey:@"numOfAds"]).intValue;
    }
    if ([config objectForKey:@"totalRetryCount"]){
        totalRetryCount = ((NSNumber *)[config objectForKey:@"totalRetryCount"]).boolValue;
    }

    if ([config objectForKey:@"retryDelay"]){
        retryDelay = ((NSNumber *)[config objectForKey:@"retryDelay"]).boolValue;
    }

    _nativeAds =  [[NSMutableArray<RNAdMobUnifiedAdContainer *> alloc]init];

    if ([config objectForKey:@"expirationPeriod"]){
        _expirationInterval = ((NSNumber *)[config objectForKey:@"expirationPeriod"]).intValue;
    }
    if ([config objectForKey:@"mediationEnabled"]){
        _isMediationEnabled = ((NSNumber *)[config objectForKey:@"mediationEnabled"]).boolValue;
    }


    //Set request options
    if ([config objectForKey:@"adChoicesPlacement"]){
        [adPlacementOptions setPreferredAdChoicesPosition:((NSNumber *)[config objectForKey:@"adChoicesPlacement"]).intValue];
    }
    if ([config objectForKey:@"mediaAspectRatio"]){
        [adMediaOptions setMediaAspectRatio:((NSNumber *)[config objectForKey:@"mediaAspectRatio"]).intValue];
    }

    if ([config objectForKey:@"videoOptions"]){
        [self configVideoOptions:[config objectForKey:@"videoOptions"]];
    }
    if ([config objectForKey:@"mediationOptions"]){
        [self configMediationOptions:[config objectForKey:@"mediationOptions"]];
    }
    if ([config objectForKey:@"targetingOptions"]){
        [self configTargetOptions:[config objectForKey:@"targetingOptions"]];
    }


    if ([config objectForKey:@"requestNonPersonalizedAdsOnly"]){
        GADCustomEventExtras *extras = [[GADCustomEventExtras alloc] init];
        bool npa = ((NSNumber *)[config objectForKey:@"requestNonPersonalizedAdsOnly"]).boolValue;
        [extras setExtras:@{@"npa": @([NSNumber numberWithInt:npa].intValue)} forLabel:@"npa"];
        [adRequest registerAdNetworkExtras:extras];
    }

    unifiedNativeAdLoadedListener = [[UnifiedNativeAdLoadedListener alloc]initWithRepo:repo nativeAds:_nativeAds tAds:_totalAds];
    return self;
}

-(void) attachAdListener:(id<AdListener>) listener {
    [attachedAdListeners addObject:listener];
}
-(void) detachAdListener:(id<AdListener>) listener{
    [attachedAdListeners removeObject:listener];
}

/* fill up repository if need. max for multi ads request is 5 base of google admob doc
 if use mediation,you can't use GADMultipleAdsAdLoaderOptions for load ads
 */
-(void) fillAds{

    int require2fill = _totalAds-((int)_nativeAds.count);

    if ( [self isLoading] || require2fill<=0){
        return;
    }
    NSMutableArray<GADAdLoaderOptions *>* options = [NSMutableArray arrayWithArray:@[adMediaOptions,adVideoOptions,adPlacementOptions]];

    if (!_isMediationEnabled) {
        GADMultipleAdsAdLoaderOptions* multipleAdsOptions = [[GADMultipleAdsAdLoaderOptions alloc] init];
        multipleAdsOptions.numberOfAds = MAX(require2fill,0);
        [options addObject:multipleAdsOptions];
    }
    adLoader = [[GADAdLoader alloc] initWithAdUnitID:_adUnitId rootViewController:nil adTypes:@[GADAdLoaderAdTypeNative] options:options];
    [adLoader setDelegate:self];

    loadingAdRequestCount = require2fill;
    if(_isMediationEnabled){
        printf("admob request count:",MIN(require2fill,5));
        for (int i = 0; i <  MIN(require2fill,5); i++)
        {
            [adLoader loadRequest:adRequest];
        }
    }else{
        [adLoader loadRequest:adRequest];
    }
}
-(RNAdMobUnifiedAdContainer*) getAd{
    long long now = (long long)([[NSDate date] timeIntervalSince1970] * 1000.0);
    RNAdMobUnifiedAdContainer *ad = nil;

    if (!(_nativeAds.count == 0)){
        //sortAds
        [_nativeAds sortUsingComparator:^NSComparisonResult(id<Comparable, NSObject>  _Nonnull obj1,
                                                            id<Comparable, NSObject>  _Nonnull obj2) {
            return [obj1 compareTo:obj2] > 0; //find lowest showCount
        }];

        NSMutableArray<RNAdMobUnifiedAdContainer *> *discardedItems = [NSMutableArray<RNAdMobUnifiedAdContainer *> array];
        for (RNAdMobUnifiedAdContainer *item in self.nativeAds) {
            if (item != nil && (now - item.loadTime) < _expirationInterval) {
                ad = item;//acceptable ad found
                break;
            }else{
                if (item.references <=0){
                    item.unifiedNativeAd = nil;
                    [discardedItems addObject:item];
                }
            }
        }
        [self.nativeAds removeObjectsInArray:discardedItems];
    }else{
        return nil;
    }
    ad.showCount += 1;
    ad.references += 1;
    [self fillAds];
    return ad;
}
-(BOOL) isLoading{
    if (adLoader != nil){
        return [adLoader isLoading] || loadingAdRequestCount>0;
    }
    return false;
}
-(NSDictionary*) hasAd{
    NSMutableDictionary*  args = [[NSMutableDictionary alloc] init];
    [args setObject:[NSNumber numberWithInteger:_nativeAds.count] forKey:_name];
    return args;
}
- (void)adLoader:(nonnull GADAdLoader *)adLoader didReceiveNativeAd:(nonnull GADNativeAd *)nativeAd {
    loadingAdRequestCount--;
    retryCount = 0;
    [unifiedNativeAdLoadedListener adLoader:adLoader didReceiveNativeAd:nativeAd];

    //to prevent a crash (check the link below), first copy attachedAdListeners into a new array
    //link:https://stackoverflow.com/questions/44648610/collection-nsarraym-was-mutated-while-being-enumerated
    for (id<AdListener> listener in [attachedAdListeners copy]){
        [listener didAdLoaded:nativeAd];
    }

    if (loadingAdRequestCount == 0){
        [self fillAds];//fill up repository if need
    }
    // The adLoader has finished loading ads, and a new request can be sent.
}


- (void)adLoader:(nonnull GADAdLoader *)adLoader didFailToReceiveAdWithError:(nonnull NSError *)error {
      if(_isMediationEnabled){
         loadingAdRequestCount--;
      }else{
         loadingAdRequestCount = 0;
      }

    [unifiedNativeAdLoadedListener adLoader:adLoader didFailToReceiveAdWithError:error];


    BOOL stopPreloading = false;
    switch (error.code) {
        case GADErrorInternalError:
        case GADErrorInvalidRequest:
            stopPreloading = true;
            break;
    }

    if (stopPreloading) {
        NSDictionary *errorDic = @{
                @"domain":error.domain,
                @"message":error.localizedDescription,
                @"code":@(error.code).stringValue
            };
            NSDictionary *event = @{
                @"error":errorDic,
            };

            [EventEmitter.sharedInstance sendEvent:CacheManager.EVENT_AD_PRELOAD_ERROR dict:event];
            for (id<AdListener> listener in [attachedAdListeners copy]){
               [listener didFailToReceiveAdWithError:error];
             }
            return;
    }

    if (retryCount >= totalRetryCount){
        NSDictionary *errorDic = @{
               @"domain":@"",
               @"message":@"reach max retry",
               @"code":@""
            };
            NSDictionary *event = @{
                @"error":errorDic,
            };
        [EventEmitter.sharedInstance sendEvent:CacheManager.EVENT_AD_PRELOAD_ERROR dict:event];
        for (id<AdListener> listener in [attachedAdListeners copy]){
           [listener didFailToReceiveAdWithError:error];
         }
        return;
    }


    if (loadingAdRequestCount == 0) {
        retryCount++;
        __weak typeof(self) weakSelf = self;
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)((retryDelay/1000) * NSEC_PER_SEC)),
                       dispatch_get_main_queue(), ^{
            [weakSelf fillAds];
        });
    }

}

-(void)configVideoOptions:(NSDictionary *)config{

    if ([config objectForKey:@"mute"]) {
        bool muted = ((NSNumber *)[config objectForKey:@"mute"]).boolValue;
        [adVideoOptions setStartMuted:muted];
    }

    if ([config objectForKey:@"clickToExpand"]) {
        bool clickToExpand = ((NSNumber *)[config objectForKey:@"clickToExpand"]).boolValue;
        [adVideoOptions setClickToExpandRequested:clickToExpand];
    }

    if ([config objectForKey:@"customControlsRequested"]) {
        bool customControlsRequested = ((NSNumber *)[config objectForKey:@"customControlsRequested"]).boolValue;
        [adVideoOptions setCustomControlsRequested:customControlsRequested];
    }
}

-(void)configTargetOptions:(NSDictionary *)config{

    if ([config objectForKey:@"targets"]){
        NSArray<NSDictionary *>* targets = (NSArray<NSDictionary *> *)[config objectForKey:@"targets"];
        for (NSDictionary* target in targets){
             [adRequest setCustomTargeting:target];
        }

        if ([config objectForKey:@"categoryExclusions"]){
            [adRequest setCategoryExclusions:(NSArray<NSString *> *)[config objectForKey:@"categoryExclusions"]];
        }
        if ([config objectForKey:@"publisherId"]){
            [adRequest setPublisherProvidedID:(NSString *)[config objectForKey:@"publisherId"]];
        }

        if ([config objectForKey:@"requestAgent"]){
            [adRequest setRequestAgent:(NSString *)[config objectForKey:@"requestAgent"]];
        }
        if ([config objectForKey:@"keywords"]){
            [adRequest setKeywords:(NSArray<NSString *> *)[config objectForKey:@"requestAgent"]];
        }
        if ([config objectForKey:@"contentUrl"]){
            [adRequest setContentURL:(NSString *)[config objectForKey:@"contentUrl"]];
        }
        if ([config objectForKey:@"neighboringContentUrls"]){
            [adRequest setNeighboringContentURLStrings:(NSArray<NSString *> *)[config objectForKey:@"neighboringContentUrls"]];
        }

    }
}

-(void)configMediationOptions:(NSDictionary *)config{
#ifdef MEDIATION_FACEBOOK
        GADFBNetworkExtras * extras = [[GADFBNetworkExtras alloc] init];

        if ([config valueForKey:@"nativeBanner"]) {
            extras.nativeAdFormat = GADFBAdFormatNative;
        } else {
            extras.nativeAdFormat = GADFBAdFormatNativeBanner;
        }

        [adRequest registerAdNetworkExtras:extras];
#endif

}

@end
