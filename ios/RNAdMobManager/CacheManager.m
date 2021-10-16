//
//  CacheManager.m
//  react-native-admob-native-ads
//
//  Created by Ali on 8/25/21.
//

#import <Foundation/Foundation.h>
#import <CacheManager.h>
#import "RNAdMobUnifiedAdQueueWrapper.h"



@implementation CacheManager{
    NSMutableDictionary *repositoriesMap;
}

+ (NSString *)EVENT_AD_PRELOAD_LOADED { return @"onAdPreloadLoaded"; }
+ (NSString *)EVENT_AD_PRELOAD_ERROR { return @"onAdPreloadError"; }

static CacheManager *_sharedInstance = nil;

+ (CacheManager*)sharedInstance
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _sharedInstance = [[self alloc] init];

    });

    return _sharedInstance;
}
- (instancetype)init{
    repositoriesMap = [[NSMutableDictionary alloc] init];
    return self;
}

-(BOOL) isLoading:(NSString*) id{
    RNAdMobUnifiedAdQueueWrapper *repo =  (RNAdMobUnifiedAdQueueWrapper *)([repositoriesMap objectForKey:id]);
    return  repo && repo.isLoading;
}
-(NSInteger)  numberOfAds:(NSString*) id{
    RNAdMobUnifiedAdQueueWrapper *repo =  (RNAdMobUnifiedAdQueueWrapper *)([repositoriesMap objectForKey:id]);
    if (repo != nil){
      return repo.nativeAds.count;
    }else{
        return 0;
    }
}
-(void) attachAdListener:(NSString*) id listener:(id<AdListener>)listener{
    RNAdMobUnifiedAdQueueWrapper *repo =  (RNAdMobUnifiedAdQueueWrapper *)([repositoriesMap objectForKey:id]);
    if (repo != nil){
        [repo attachAdListener:listener];
    }
  
}
-(void) detachAdListener:(NSString*) id{
    RNAdMobUnifiedAdQueueWrapper *repo =  (RNAdMobUnifiedAdQueueWrapper *)([repositoriesMap objectForKey:id]);
    if (repo != nil){
        [repo detachAdListener];
    }
}
-(NSDictionary*)registerRepo:(NSDictionary*) config rootVC:(UIViewController*)rootVC{
    
    NSMutableDictionary*  args = [[NSMutableDictionary alloc] init];
    NSString* repoId = nil;
    if (![config objectForKey:@"adUnitId"]) {
        [args setObject:[NSNumber numberWithBool:false] forKey:@"success"];
        [args setObject:@"the adUnitId has to be set in config" forKey:@"error"];
    }
    
    if ([config objectForKey:@"name"]) {
         repoId = [config objectForKey:@"name"];
    }else{
        if ([config objectForKey:@"adUnitId"]) {
            repoId = [config objectForKey:@"adUnitId"];
        }
    }
    if (repoId != nil){
             
                if (![repositoriesMap objectForKey:repoId]) {
                    RNAdMobUnifiedAdQueueWrapper *repo = [[RNAdMobUnifiedAdQueueWrapper alloc] initWithConfig:config repo:repoId rootVC:rootVC];
                    [repositoriesMap setObject:repo forKey:repoId];
                
                    [args setObject:[NSNumber numberWithBool:YES] forKey:@"success"];
                    [args setObject:repoId forKey:@"repo"];
                }else{
                    [args setObject:[NSNumber numberWithBool:NO] forKey:@"success"];
                    [args setObject:@"the given repo has been registered before" forKey:@"error"];
                }
                
    }else{
        [args setObject:[NSNumber numberWithBool:NO] forKey:@"success"];
        [args setObject:@"the adUnitId or name has to be set in config" forKey:@"error"];
    }
    return  args;
    
}
-(void) unRegisterRepo:(NSString*) id{
    [repositoriesMap removeObjectForKey: id];
}
-(void) resetCache{
    [repositoriesMap removeAllObjects];
}
-(void) configAdLoader:(NSString*) id rootVC:(UIViewController *) rootVC{
    RNAdMobUnifiedAdQueueWrapper *repo =  (RNAdMobUnifiedAdQueueWrapper *)([repositoriesMap objectForKey:id]);
    [repo configAdLoaderOption:rootVC];
}
-(void) requestAds:(NSString*) id{
    RNAdMobUnifiedAdQueueWrapper *repo =  (RNAdMobUnifiedAdQueueWrapper *)([repositoriesMap objectForKey:id]);
    [repo fillAds];
}

-(BOOL) isRegistered:(NSString*) id{
    RNAdMobUnifiedAdQueueWrapper *repo =  (RNAdMobUnifiedAdQueueWrapper *)([repositoriesMap objectForKey:id]);
  return repo;
}
-(RNAdMobUnifiedAdContainer*) getNativeAd:(NSString*) id{
    RNAdMobUnifiedAdQueueWrapper *repo =  (RNAdMobUnifiedAdQueueWrapper *)([repositoriesMap objectForKey:id]);
    if (repo != nil) {
        return [repo getAd];
    }else{
        return nil;
    }
}
-(NSDictionary*) hasAd:(NSString*) id{
    RNAdMobUnifiedAdQueueWrapper *repo =  (RNAdMobUnifiedAdQueueWrapper *)([repositoriesMap objectForKey:id]);
    if (repo != nil) {
        return [repo hasAd];
    }else{
        NSMutableDictionary*  args = [[NSMutableDictionary alloc] init];
        [args setValue:0 forKey:id];
        return  args;
    }
}
@end
