#import "RNAdmobNativeAdsManager.h"
#import "RNAdMobUtils.h"
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
        } else if ([rating isEqualToString:@""]) {
            [[[GADMobileAds sharedInstance] requestConfiguration] setMaxAdContentRating:NULL];
            
        }
        
    };
    
    if ([[config allKeys] containsObject:@"tagForChildDirectedTreatment"]) {
        NSNumber *tag = [config valueForKey:@"tagForChildDirectedTreatment"];
        
        if (tag.intValue == 0) {
            [[[GADMobileAds sharedInstance] requestConfiguration] tagForChildDirectedTreatment:false];
            
        } else {
            [[[GADMobileAds sharedInstance] requestConfiguration] tagForChildDirectedTreatment:true];
            
        }
    };
    
    if ([[config allKeys] containsObject:@"tagForUnderAgeConsent"]) {
        NSNumber *tagC = [config valueForKey:@"tagForUnderAgeConsent"];
        
        if (tagC.intValue == 0) {
            [[[GADMobileAds sharedInstance] requestConfiguration] tagForUnderAgeOfConsent:false];
        } else {
            [[[GADMobileAds sharedInstance] requestConfiguration] tagForUnderAgeOfConsent:true];
        }
    };
    
    if ([[config allKeys] containsObject:@"testDeviceIds"]) {
        
        NSArray *testDevices = RNAdMobProcessTestDevices([config valueForKey:@"testDeviceIds"], kDFPSimulatorID);
        [[[GADMobileAds sharedInstance] requestConfiguration] setTestDeviceIdentifiers:testDevices];
        
    };
    

}

RCT_EXPORT_METHOD(isTestDevice:(RCTPromiseResolveBlock)resolve
rejecter:(RCTPromiseRejectBlock)reject) {

    resolve(@TRUE);
}





@end
