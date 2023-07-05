//
//  CacheManager.h
//  Pods
//
//  Created by Ali on 8/25/21.
//

#ifndef CacheManager_h
#define CacheManager_h
#import "AdListener.h"
#import "RNAdMobUnifiedAdContainer.h"



@interface CacheManager:NSObject

+ (CacheManager*)sharedInstance;
+ (NSString *)EVENT_AD_PRELOAD_LOADED:(NSString *)name;
+ (NSString *)EVENT_AD_PRELOAD_ERROR:(NSString *)name;
+ (NSString *)EVENT_AD_CLOSED:(NSString *)name;
+ (NSString *)EVENT_AD_OPEN:(NSString *)name;
+ (NSString *)EVENT_AD_CLICKED:(NSString *)name;
+ (NSString *)EVENT_AD_IMPRESSION:(NSString *)name;


-(BOOL) isLoading:(NSString*) id;
-(NSInteger)  numberOfAds:(NSString*) id;
-(void) attachAdListener:(NSString*) id listener:(id<AdListener>)listener;
-(void) detachAdListener:(NSString*) id listener:(id<AdListener>)listener;
-(NSDictionary*)registerRepo:(NSDictionary*) config;
-(void) unRegisterRepo:(NSString*) repo;
-(void) resetCache;
-(void) requestAds:(NSString*) repo;

-(BOOL) isRegistered:(NSString*) repo;
-(RNAdMobUnifiedAdContainer*) getNativeAd:(NSString*) repo;
-(NSDictionary*) hasAd:(NSString*) repo;

@end
#endif /* CacheManager_h */
