//
//  RNAdMobUnifiedAdQueueWrapper.m
//  react-native-admob-native-ads
//
//  Created by Ali on 8/25/21.
//

#import <Foundation/Foundation.h>
#import "RNAdMobUnifiedAdQueueWrapper.h"
#import "OnUnifiedNativeAdLoadedListener.h"
#import "RNAdMobUnifiedAdContainer.h"
#import "EventEmitter.h"
#import "CacheManager.h"
@import GoogleMobileAds;

@implementation RNAdMobUnifiedAdQueueWrapper{
    GADAdLoader* adLoader;
    GADRequest* adRequest;
    id<AdListener> attachedAdListener;
    OnUnifiedNativeAdLoadedListener* unifiedNativeAdLoadedListener;
    GADVideoOptions* adVideoOptions;
    GADNativeAdMediaAdLoaderOptions* adMediaOptions;
    GADNativeAdViewAdOptions* adPlacementOptions;
    BOOL isInLoading;//true after all ads loaded
}

-(instancetype)initWithConfig:(NSDictionary *)config repo:(NSString *)repo rootVC:(UIViewController*)rootVC{
    if (self = [super init])  {
        self.npa = true;
        self.totalAds = 5;
        self.expirationInterval = 3600000; // in ms
        self.muted = true;
        self.mediation = false;
        isInLoading = false;
        adVideoOptions = [[GADVideoOptions alloc]init];
        adMediaOptions = [[GADNativeAdMediaAdLoaderOptions alloc] init];
        adPlacementOptions = [[GADNativeAdViewAdOptions alloc]init];
    }
    
    
    _adUnitId = [config objectForKey:@"adUnitId"] ;
    _name = repo;
    if ([config objectForKey:@"numOfAds"]){
        _totalAds = ((NSNumber *)[config objectForKey:@"numOfAds"]).intValue;
    }
    
    _nativeAds =  [[NSMutableArray<RNAdMobUnifiedAdContainer *> alloc]init];
    
    
    if ([config objectForKey:@"mute"]){
        _muted = ((NSNumber *)[config objectForKey:@"mute"]).boolValue;
    }
    if ([config objectForKey:@"clickToExpand"]) {
        _clickToExpand = ((NSNumber *)[config objectForKey:@"clickToExpand"]).boolValue;
    }
    
    if ([config objectForKey:@"customControlsRequested"]) {
        _customControlsRequested = ((NSNumber *)[config objectForKey:@"customControlsRequested"]).boolValue;
    }
    
    if ([config objectForKey:@"expirationPeriod"]){
        _expirationInterval = ((NSNumber *)[config objectForKey:@"expirationPeriod"]).intValue;
    }
    if ([config objectForKey:@"mediationEnabled"]){
        _mediation = ((NSNumber *)[config objectForKey:@"mediationEnabled"]).boolValue;
    }
    if ([config objectForKey:@"adChoicesPlacement"]){
        _adChoicesPlacement = ((NSNumber *)[config objectForKey:@"adChoicesPlacement"]);
    }
    if ([config objectForKey:@"mediaAspectRatio"]){
        _mediaAspectRatio = ((NSNumber *)[config objectForKey:@"mediaAspectRatio"]);
    }
    if ([config objectForKey:@"nonPersonalizedAdsOnly"]){
        _npa = ((NSNumber *)[config objectForKey:@"nonPersonalizedAdsOnly"]).boolValue;
        
        adRequest = [GADRequest request];
        GADCustomEventExtras *extras = [[GADCustomEventExtras alloc] init];
        
        [extras setExtras:@{@"npa": @([NSNumber numberWithInt:_npa].intValue)} forLabel:@"npa"];
        [adRequest registerAdNetworkExtras:extras];
        
    }else{
        adRequest = [GADRequest request];
    }
    
    unifiedNativeAdLoadedListener = [[OnUnifiedNativeAdLoadedListener alloc]initWithRepo:repo nativeAds:_nativeAds tAds:_totalAds];
    
    [self configAdLoaderOption:rootVC];
    
    return self;
}
-(void) configAdLoaderOption:(UIViewController *) rootVC{
    //https://developers.google.com/admob/ios/native/options#objective-c_1
    self.rootVC = rootVC;
    
    GADVideoOptions* adVideoOptions = [[GADVideoOptions alloc]init];
    [adVideoOptions setStartMuted:_muted];
    [adVideoOptions setClickToExpandRequested:_clickToExpand];
    [adVideoOptions setCustomControlsRequested:_customControlsRequested];
    
    GADNativeAdViewAdOptions* adPlacementOptions = [[GADNativeAdViewAdOptions alloc]init];
    if ([_adChoicesPlacement isEqualToNumber:@0]) {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionTopLeftCorner];
    } else if ([_adChoicesPlacement isEqualToNumber:@1]) {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionTopRightCorner];
    }  else if ([_adChoicesPlacement isEqualToNumber:@2]) {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionBottomRightCorner];
    }  else if ([_adChoicesPlacement isEqualToNumber:@3]) {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionBottomLeftCorner];
    } else {
        [adPlacementOptions setPreferredAdChoicesPosition:GADAdChoicesPositionTopRightCorner];
    }
    
    
    GADNativeAdMediaAdLoaderOptions* adMediaOptions = [[GADNativeAdMediaAdLoaderOptions alloc] init];
    if ([_mediaAspectRatio isEqualToNumber:@0]) {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioUnknown];
    } else if ([_mediaAspectRatio isEqualToNumber:@1]) {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioAny];
    }  else if ([_mediaAspectRatio isEqualToNumber:@2]) {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioLandscape];
    }  else if ([_mediaAspectRatio isEqualToNumber:@3]) {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioPortrait];
    } else {
        [adMediaOptions setMediaAspectRatio:GADMediaAspectRatioSquare];
    }
    
    
}
-(void) attachAdListener:(id<AdListener>) listener {
    attachedAdListener = listener;
}
-(void) detachAdListener{
    attachedAdListener = nil;
}

-(void) fillAds{
    if ( ![self isLoading] && _totalAds-_nativeAds.count>0){
        GADMultipleAdsAdLoaderOptions* multipleAdsOptions = [[GADMultipleAdsAdLoaderOptions alloc] init];
        multipleAdsOptions.numberOfAds = MAX(_totalAds-_nativeAds.count,0);
        adLoader = [[GADAdLoader alloc] initWithAdUnitID:_adUnitId rootViewController:_rootVC adTypes:@[kGADAdLoaderAdTypeNative] options:@[adMediaOptions,adVideoOptions,adPlacementOptions,multipleAdsOptions]];
        [adLoader setDelegate:self];
        [adLoader loadRequest:adRequest];
        isInLoading = true;
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
        return [adLoader isLoading] || isInLoading;
    }
    return false;
}
-(NSDictionary*) hasAd{
    NSMutableDictionary*  args = [[NSMutableDictionary alloc] init];
    [args setObject:[NSNumber numberWithInteger:_nativeAds.count] forKey:_name];
    return args;
}
- (void)adLoader:(nonnull GADAdLoader *)adLoader didReceiveNativeAd:(nonnull GADNativeAd *)nativeAd {
    [unifiedNativeAdLoadedListener adLoader:adLoader didReceiveNativeAd:nativeAd];
    [nativeAd setDelegate:self];
    [attachedAdListener didAdLoaded:nativeAd];
}
- (void)adLoaderDidFinishLoading:(GADAdLoader *) adLoader {
    isInLoading = false;
    // The adLoader has finished loading ads, and a new request can be sent.
}


- (void)adLoader:(nonnull GADAdLoader *)adLoader didFailToReceiveAdWithError:(nonnull NSError *)error {
    [unifiedNativeAdLoadedListener adLoader:adLoader didFailToReceiveAdWithError:error];
    BOOL stopPreloading = false;
    switch (error.code) {
        case GADErrorInternalError:
        case GADErrorInvalidRequest:
            stopPreloading = true;
            break;
    }
    if (attachedAdListener == nil) {
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
        }
        return;
    }
    [attachedAdListener didFailToReceiveAdWithError:error];
}

- (void)nativeAdDidRecordImpression:(nonnull GADNativeAd *)nativeAd{
    if (attachedAdListener == nil) return;
    [attachedAdListener nativeAdDidRecordImpression:nativeAd];
}

- (void)nativeAdDidRecordClick:(nonnull GADNativeAd *)nativeAd{
    if (attachedAdListener == nil) return;
    [attachedAdListener nativeAdDidRecordClick:nativeAd];
}

- (void)nativeAdWillPresentScreen:(nonnull GADNativeAd *)nativeAd{
    if (attachedAdListener == nil) return;
    [attachedAdListener nativeAdWillPresentScreen:nativeAd];
}

- (void)nativeAdWillDismissScreen:(nonnull GADNativeAd *)nativeAd{
    if (attachedAdListener == nil) return;
    [attachedAdListener nativeAdWillDismissScreen:nativeAd];
}

- (void)nativeAdDidDismissScreen:(nonnull GADNativeAd *)nativeAd{
    if (attachedAdListener == nil) return;
    [attachedAdListener nativeAdDidDismissScreen:nativeAd];
}


- (void)nativeAdIsMuted:(nonnull GADNativeAd *)nativeAd{
    if (attachedAdListener == nil) return;
    [attachedAdListener nativeAdIsMuted:nativeAd];
    
}



@end
