//
//  RNAdMobUnifiedAdContainer.h
//  Pods
//
//  Created by Ali on 8/25/21.
//

#ifndef RNAdMobUnifiedAdContainer_h
#define RNAdMobUnifiedAdContainer_h

#import "RNGADNativeView.h"
#import "Comparable.h"

@interface RNAdMobUnifiedAdContainer : NSObject<Comparable>

- (instancetype)initWithAd:(GADNativeAd *)nativeAd loadTime:(long long) loadTime  showCount:(int)showCount;

@property(nonatomic, readwrite) long long  loadTime;
@property(nonatomic, readwrite) int showCount;
@property(nonatomic, readwrite) int references;
@property(nonatomic, readwrite) GADNativeAd *unifiedNativeAd;
@end
#endif /* RNAdMobUnifiedAdContainer_h */
