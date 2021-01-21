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

#import "RNGADImageView.h"
#import <UIKit/UIImageView.h>

@implementation RNGADImageView: RCTViewManager

RCT_EXPORT_MODULE(RNGADImageView);

-(UIImageView *)view
{
    
 return [[UIImageView alloc] init];
    
}


@end
