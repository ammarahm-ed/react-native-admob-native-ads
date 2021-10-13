//
//  EventEmitter.m
//  react-native-admob-native-ads
//
//  Created by Ali on 8/27/21.
//

#import <Foundation/Foundation.h>
#import "EventEmitter.h"

@implementation EventEmitter
{
  bool hasListeners;
}
static EventEmitter *_sharedInstance = nil;

+ (EventEmitter*)sharedInstance
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        _sharedInstance = [[self alloc] init];

    });

    return _sharedInstance;
}
-(NSArray<NSString *> *)supportedEvents{
    return @[
        @"onAdPreloadLoaded",
        @"onAdPreloadError",
        @"onAdFailedToLoad",
        @"onAdClicked",
        @"onAdClosed",
        @"onAdOpened",
        @"onAdImpression",
        @"onAdLoaded",
        @"onAdLeftApplication",
        @"onUnifiedNativeAdLoaded"
    ];
}
// Will be called when this module's first listener is added.
-(void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}
- (void)sendEvent:(NSString *) name dict:(NSDictionary *)dict
{

    if (hasListeners) { // Only send events if anyone is listening
      [self sendEventWithName:name body:dict];
    }
}
@end

