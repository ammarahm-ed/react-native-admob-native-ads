#import "RNAdmobNativeAdsManager.h"
#import "RNNativeAdMobUtils.h"

@import GoogleMobileAds;

@implementation RNAdmobNativeAdsManager

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(setRequestConfiguration:(NSDictionary *)config)
{
    if ([[config allKeys] containsObject:@"maxAdContentRating"]) {
        NSString *rating = [config valueForKey:@"maxAdContentRating"];
        if ([rating isEqualToString:@"G"]) {
            [[[GADMobileAds sharedInstance] requestConfiguration] setMaxAdContentRating:GADMaxAdContentRatingGeneral];
        } else  if ([rating isEqualToString:@"PG"]) {
            [[[GADMobileAds sharedInstance] requestConfiguration] setMaxAdContentRating:GADMaxAdContentRatingParentalGuidance];
        } else  if ([rating isEqualToString:@"MA"]) {
            [[[GADMobileAds sharedInstance] requestConfiguration] setMaxAdContentRating:GADMaxAdContentRatingMatureAudience];
        } else  if ([rating isEqualToString:@"T"]) {
            [[[GADMobileAds sharedInstance] requestConfiguration] setMaxAdContentRating:GADMaxAdContentRatingTeen];
        } else if ([rating isEqualToString:@""] || [rating isEqualToString:@"UNSPECIFIED"]) {
            [[[GADMobileAds sharedInstance] requestConfiguration] setMaxAdContentRating:NULL];
        }
    };
    
    if ([[config allKeys] containsObject:@"tagForChildDirectedTreatment"]) {
        NSNumber *tag = [config valueForKey:@"tagForChildDirectedTreatment"];
        [[[GADMobileAds sharedInstance] requestConfiguration] tagForChildDirectedTreatment:tag.boolValue];
    };
    
    if ([[config allKeys] containsObject:@"tagForUnderAgeConsent"]) {
        NSNumber *tagC = [config valueForKey:@"tagForUnderAgeConsent"];
        [[[GADMobileAds sharedInstance] requestConfiguration] tagForUnderAgeOfConsent:tagC.boolValue];
    };
    
    if ([[config allKeys] containsObject:@"testDeviceIds"]) {
        NSArray *testDevices = RNAdMobProcessTestDevices([config valueForKey:@"testDeviceIds"], kGAMSimulatorID);
        [[[GADMobileAds sharedInstance] requestConfiguration] setTestDeviceIdentifiers:testDevices];
    };
}

RCT_EXPORT_METHOD(isTestDevice:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    resolve(@TRUE);
}


@end
