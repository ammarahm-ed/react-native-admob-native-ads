//
//  RNAdMobUnifiedAdQueueWrapper.h
//  Pods
//
//  Created by Ali on 8/25/21.
//

#ifndef RNAdMobUnifiedAdQueueWrapper_h
#define RNAdMobUnifiedAdQueueWrapper_h
#import "RNAdMobUnifiedAdContainer.h"
#import "AdListener.h"

@interface RNAdMobUnifiedAdQueueWrapper:NSObject<GADNativeAdLoaderDelegate,GADNativeAdDelegate>

-(instancetype)initWithConfig:(NSDictionary *)config repo:(NSString *)repo;

@property(nonatomic, readwrite) NSString* adUnitId;
@property(nonatomic, readwrite) NSString* name;
@property(nonatomic, readwrite) int totalAds;
@property(nonatomic, readwrite) long expirationInterval; // in ms
@property(nonatomic, readwrite) BOOL isMediationEnabled;
@property(nonatomic, readwrite) UIViewController* rootVC;

@property(nonatomic, readwrite) NSMutableArray<RNAdMobUnifiedAdContainer *> *nativeAds;

-(void) attachAdListener:(id<AdListener>) listener;
-(void) detachAdListener:(id<AdListener>) listener;
-(void) fillAds;
-(RNAdMobUnifiedAdContainer*) getAd;
-(BOOL) isLoading;
-(NSDictionary*) hasAd;


@end

#endif /* RNAdMobUnifiedAdQueueWrapper_h */
