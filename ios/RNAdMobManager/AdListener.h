//
//  AdListener.h
//  Pods
//
//  Created by Ali on 8/25/21.
//

#ifndef AdListener_h
#define AdListener_h
@import GoogleMobileAds;
@protocol AdListener <GADNativeAdDelegate>
@optional
- (void)didAdLoaded:(nonnull GADNativeAd *)nativeAd;
- (void)didFailToReceiveAdWithError:(nonnull NSError *)error;
@end

#endif /* AdListener_h */
