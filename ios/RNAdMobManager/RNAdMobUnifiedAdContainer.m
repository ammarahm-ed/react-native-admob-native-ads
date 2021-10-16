//
//  RNAdMobUnifiedAdContainer.m
//  react-native-admob-native-ads
//
//  Created by Ali on 8/26/21.
//

#import <Foundation/Foundation.h>
#import "RNAdMobUnifiedAdContainer.h"

@implementation RNAdMobUnifiedAdContainer

- (instancetype)initWithAd:(GADNativeAd *)nativeAd loadTime:(long long) loadTime  showCount:(int)showCount{
    _unifiedNativeAd = nativeAd;
    _loadTime = loadTime;
    _showCount = showCount;
    _references = 0;

    return self;

}
- (int)compareTo:(id<Comparable,NSObject>)object {
    RNAdMobUnifiedAdContainer *container = (RNAdMobUnifiedAdContainer *)object;

    if (self.showCount > container.showCount)
        return 1;
    else if (self.showCount < container.showCount)
        return -1;
    return 0;
}

- (BOOL)isEqual:(id<Comparable,NSObject>)object {
    RNAdMobUnifiedAdContainer *container = (RNAdMobUnifiedAdContainer *)object;
    return self.showCount == container.showCount;
}

@end
