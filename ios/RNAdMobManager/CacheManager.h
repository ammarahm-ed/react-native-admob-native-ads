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
+ (NSString*)  EVENT_AD_PRELOAD_LOADED;
+ (NSString*)  EVENT_AD_PRELOAD_ERROR;

-(BOOL) isLoading:(NSString*) id;
-(NSInteger)  numberOfAds:(NSString*) id;
-(void) attachAdListener:(NSString*) id listener:(id<AdListener>)listener;
-(void) detachAdListener:(NSString*) id;
-(NSDictionary*)registerRepo:(NSDictionary*) config rootVC:(UIViewController*)rootVC;
-(void) configAdLoader:(NSString*) id rootVC:(UIViewController *) rootVC;
-(void) unRegisterRepo:(NSString*) repo;
-(void) resetCache;
-(void) requestAds:(NSString*) repo;

-(BOOL) isRegistered:(NSString*) repo;
-(RNAdMobUnifiedAdContainer*) getNativeAd:(NSString*) repo;
-(NSDictionary*) hasAd:(NSString*) repo;

@end
#endif /* CacheManager_h */
