//
//  RCTViewManager+RNGADMediaViewManager.m
//  DoubleConversion
//
//  Created by Ammar Ahmed on 1/29/1399 AP.
//
#import "RNGADImageView.h"

#import <UIKit/UIImageView.h>

@implementation RNGADImageView: RCTViewManager

RCT_EXPORT_MODULE(RNGADImageView);

-(UIImageView *)view
{
    
 return [[UIImageView alloc] init];
    
}


@end
