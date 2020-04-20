//
//  RCTViewManager+RNGADMediaViewManager.m
//  DoubleConversion
//
//  Created by Ammar Ahmed on 1/29/1399 AP.
//

#import "RCTViewManager.h"

#if __has_include(<React/RCTBridge.h>)
#import <React/RCTBridge.h>
#import <React/RCTUIManager.h>
#import <React/RCTEventDispatcher.h>
#else
#import "RCTBridge.h"
#import "RCTUIManager.h"
#import "RCTEventDispatcher.h"
#endif

#import "RNGADMediaViewManager.h"

@import GoogleMobileAds;

@implementation RNGADMediaViewManager: RCTViewManager

RCT_EXPORT_MODULE(MediaView);

-(UIView *)view
{
    
    
 return [[GADMediaView alloc] init];
    
}


@end
