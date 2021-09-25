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
}

-(instancetype)initWithConfig:(NSDictionary *)config repo:(NSString *)repo rootVC:(UIViewController*)rootVC{
    if (self = [super init])  {
        self.npa = true;
        self.totalAds = 5;
        self.expirationInterval = 3600000; // in ms
        self.muted = true;
        self.mediation = false;
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
    if ([config objectForKey:@"expirationPeriod"]){
        _expirationInterval = ((NSNumber *)[config objectForKey:@"expirationPeriod"]).intValue;
    }
    if ([config objectForKey:@"mediationEnabled"]){
        _mediation = ((NSNumber *)[config objectForKey:@"mediationEnabled"]).boolValue;
    }
    if ([config objectForKey:@"nonPersonalizedAdsOnly"]){
        _npa = ((NSNumber *)[config objectForKey:@"nonPersonalizedAdsOnly"]).boolValue;
        
        adRequest = [GADRequest request];
        GADCustomEventExtras *extras = [[GADCustomEventExtras alloc] init];
        
        //MARK:TODO require test!
        [extras setExtras:@{@"npa": @([NSNumber numberWithInt:_npa].intValue)} forLabel:@"npa"];
        [adRequest registerAdNetworkExtras:extras];
        
    }else{
        adRequest = [GADRequest request];
    }
    
    unifiedNativeAdLoadedListener = [[OnUnifiedNativeAdLoadedListener alloc]initWithRepo:repo nativeAds:_nativeAds tAds:_totalAds];
    
    [self configAdLoader:rootVC];
    
    return self;
}
-(void) configAdLoader:(UIViewController *) rootVC {
    //https://developers.google.com/admob/ios/native/options#objective-c_1
    GADVideoOptions* adVideoOptions = [[GADVideoOptions alloc]init];
    [adVideoOptions setStartMuted:_muted];
    
    GADNativeAdViewAdOptions* adPlacementOptions = [[GADNativeAdViewAdOptions alloc]init];
    adPlacementOptions.preferredAdChoicesPosition = GADAdChoicesPositionTopRightCorner;
    
    
    GADMultipleAdsAdLoaderOptions* multipleAdsOptions = [[GADMultipleAdsAdLoaderOptions alloc] init];
    multipleAdsOptions.numberOfAds = _totalAds;
    
    adLoader = [[GADAdLoader alloc] initWithAdUnitID:_adUnitId rootViewController:rootVC adTypes:@[kGADAdLoaderAdTypeNative] options:@[adVideoOptions,adPlacementOptions,multipleAdsOptions]];
    
    [adLoader setDelegate:self];
    
}
-(void) attachAdListener:(id<AdListener>) listener {
    attachedAdListener = listener;
}
-(void) detachAdListener{
    attachedAdListener = nil;
}
-(void) loadAds{
    for (int i = 0; i<_totalAds; i++){
        [adLoader loadRequest:adRequest];
    }
    //MARK:TODO require test!
    //https://ads-developers.googleblog.com/2017/12/loading-multiple-native-ads-in-google.html
    //https://developers.google.com/admob/ios/api/reference/Classes/GADMultipleAdsAdLoaderOptions
}
-(void) loadAd{
    
    [adLoader loadRequest:adRequest];
    [self fillAd];
}
-(void) fillAd{
    if ( [self isLoading]){
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
    [self fillAd];
    return ad;
}
-(BOOL) isLoading{
    if (adLoader != nil){
        return [adLoader isLoading];
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
    [attachedAdListener didAdLoaded:nativeAd];
    [nativeAd setDelegate:self];
}

- (void)adLoader:(nonnull GADAdLoader *)adLoader didFailToReceiveAdWithError:(nonnull NSError *)error {
    [unifiedNativeAdLoadedListener adLoader:adLoader didFailToReceiveAdWithError:error];
    NSString *errorMessage = @"";
    BOOL stopPreloading = false;
    switch (error.code) {
        case GADErrorInternalError:
            stopPreloading = true;
            errorMessage = @"Internal error, an invalid response was received from the ad server.";
            break;
        case GADErrorInvalidRequest:
            stopPreloading = true;
            errorMessage = @"Invalid ad request, possibly an incorrect ad unit ID was given.";
            break;
        case GADErrorNetworkError:
            errorMessage = @"The ad request was unsuccessful due to network connectivity.";
            break;
        case GADErrorNoFill:
            errorMessage = @"The ad request was successful, but no ad was returned due to lack of ad inventory.";
            break;
    }
    if (attachedAdListener == nil) {
        if (stopPreloading) {
            
            NSDictionary *errorDic = @{
                @"errorMessage":error.localizedDescription,
                @"message":errorMessage,
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
