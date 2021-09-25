//
//  OnUnifiedNativeAdLoadedListener.h
//  Pods
//
//  Created by Ali on 8/25/21.
//

#ifndef OnUnifiedNativeAdLoadedListener_h
#define OnUnifiedNativeAdLoadedListener_h
#import "RNAdMobUnifiedAdContainer.h"
@import GoogleMobileAds;

@interface OnUnifiedNativeAdLoadedListener : NSObject<GADNativeAdLoaderDelegate>

- (instancetype)initWithRepo:(NSString *)repo nativeAds:(NSMutableArray<RNAdMobUnifiedAdContainer *> *) nativeAds  tAds:(int)tAds;
@property(nonatomic, readwrite) NSString* repo;
@property(nonatomic, readwrite) NSMutableArray<RNAdMobUnifiedAdContainer *> * nativeAds;
@property(nonatomic, readwrite) int totalAds;
@end
#endif /* OnUnifiedNativeAdLoadedListener_h */
