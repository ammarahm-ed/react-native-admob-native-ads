#import "RNGADNativeView.h"
#import "RNAdMobUtils.h"
#import "GADTMediumTemplateView.h"
#import "GADTSmallTemplateView.h"
#import <React/RCTBridge.h>
#import <React/RCTRootView.h>
#import <React/RCTRootViewDelegate.h>
#import <React/RCTViewManager.h>

@import GoogleMobileAds;


@implementation RNGADNativeView

NSString *btnBackgroundColor = @"#0077cc";
NSString *btnBorderColor = @"#0077cc";
NSString *btnTextColor = @"#ffffff";
NSNumber *btnBorderRadius = 0;
NSNumber *btnBorderWidth = 0;

NSString *bgBackgroundColor = @"#ffffff";
NSString *bgBorderColor = @"#ffffff";
NSNumber *bgBorderRadius = 0;
NSNumber *bgBorderWidth = 0;

NSString *primaryTextColor = @"#000000";
NSString *secondaryTextColor = @"#a9a9a9";
NSString *tertiaryTextColor = @"#a9a9a9";

GADTTemplateView *nativeAdView;


- (void)setAdSize:(NSString *)adSize
{
    
    NSLog(@"Setting ad size %@", adSize);
    _adSize = adSize;
}

- (void)setTestDevices:(NSArray *)testDevices
{
    _testDevices = RNAdMobProcessTestDevices(testDevices, kDFPSimulatorID);
}


- (void)setButtonStyle:(NSDictionary *)buttonStyle
{
    btnBackgroundColor = [buttonStyle objectForKey:@"backgroundColor"];
    btnTextColor = [buttonStyle objectForKey:@"textColor"];
    btnBorderColor = [buttonStyle objectForKey:@"borderColor"];
    btnBorderWidth =  [buttonStyle objectForKey:@"borderWidth"];
    btnBorderRadius = [buttonStyle objectForKey:@"borderRadius"];
    
    [self setStylesForButton:btnBackgroundColor arg2:btnTextColor arg3:btnBorderColor arg4:btnBorderWidth arg5:btnBorderRadius];
    
}

- (void)setBackgroundStyle:(NSDictionary *)backgroundStyle
{
    bgBackgroundColor = [backgroundStyle objectForKey:@"backgroundColor"];
    bgBorderColor = [backgroundStyle objectForKey:@"borderColor"];
    bgBorderWidth =  [backgroundStyle objectForKey:@"borderWidth"];
    bgBorderRadius = [backgroundStyle objectForKey:@"borderRadius"];
    
    [self setStylesForBackground:bgBackgroundColor arg3:bgBorderColor arg4:bgBorderWidth arg5:bgBorderRadius];
    
}

- (void)setHeadlineTextColor:(NSString *)headlineTextColor
{
    primaryTextColor = headlineTextColor;
    
    [self setColorForHeadlineText:primaryTextColor];
    
}

- (void)setDescriptionTextColor:(NSString *)descriptionTextColor
{
    secondaryTextColor =descriptionTextColor;
    [self setColorForDescriptionText:secondaryTextColor];
    
    
}

- (void)setAdvertiserTextColor:(NSString *)advertiserTextColor
{
    tertiaryTextColor = advertiserTextColor;
    [self setColorForAdvertiserText:tertiaryTextColor];
}

- (void)setColorForHeadlineText:(NSString *)color {
    nativeAdView.primaryTextView.textColor = [self colorWithHexString:primaryTextColor];
}

- (void)setColorForDescriptionText:(NSString *)color {
    nativeAdView.secondaryTextView.textColor = [self colorWithHexString:secondaryTextColor];
}
- (void)setColorForAdvertiserText:(NSString *)color {
    nativeAdView.tertiaryTextView.textColor = [self colorWithHexString:color];
}


-(void) setStylesForBackground:(NSString *)backgroundColor
                          arg3:(NSString *)borderColor
                          arg4:(NSNumber *)borderWidth
                          arg5:(NSNumber *)borderRadius
{
    
    UIColor *bgColor = [self colorWithHexString:backgroundColor];
    nativeAdView.backgroundView.backgroundColor = bgColor;
    nativeAdView.rootView.backgroundColor = bgColor;
    nativeAdView.mediaView.backgroundColor = bgColor;
    nativeAdView.primaryTextView.superview.backgroundColor = bgColor;
    
    nativeAdView.layer.borderColor = [self colorWithHexString:borderColor].CGColor;
    nativeAdView.layer.borderWidth = borderWidth.floatValue;
    nativeAdView.layer.cornerRadius = borderRadius.floatValue;
    
    nativeAdView.backgroundView.layer.cornerRadius = borderRadius.floatValue;
    nativeAdView.rootView.layer.cornerRadius = borderRadius.floatValue;
    
    [nativeAdView.layer setNeedsDisplay];
    
}


- (void)setStylesForButton:(NSString *)backgroundColor
                      arg2:(NSString *)textColor
                      arg3:(NSString *)borderColor
                      arg4:(NSNumber *)borderWidth
                      arg5:(NSNumber *)borderRadius
{
    nativeAdView.callToActionView.backgroundColor = [self colorWithHexString:backgroundColor];
    

    for (UIView *view in [nativeAdView.callToActionView subviews]) {
        //Check if the view is of UILabel class
        if ([view isKindOfClass:[UILabel class]]) {
            //Cast the view to a UILabel
            UILabel *label = (UILabel *)view;
            //Set the color to label
            label.textColor = [self colorWithHexString:textColor];
        }
    }
    
    nativeAdView.callToActionView.layer.borderColor = [self colorWithHexString:borderColor].CGColor;
    
    
    nativeAdView.callToActionView.layer.borderWidth = borderWidth.floatValue;
    nativeAdView.callToActionView.layer.cornerRadius = borderRadius.floatValue;
    
    [nativeAdView.callToActionView.layer setNeedsDisplay];
    
    
    nativeAdView.adBadge.backgroundColor =[self colorWithHexString:backgroundColor];
    nativeAdView.adBadge.layer.borderColor = [self colorWithHexString:borderColor].CGColor;
    nativeAdView.adBadge.textColor =[self colorWithHexString:textColor];
    nativeAdView.adBadge.layer.borderWidth = borderWidth.floatValue;
    nativeAdView.adBadge.layer.cornerRadius = borderRadius.floatValue;
    
    [nativeAdView.adBadge.layer setNeedsDisplay];
}

- (UIColor *)colorWithHexString:(NSString *)hexString {
    unsigned rgbValue = 0;
    NSScanner *scanner = [NSScanner scannerWithString:hexString];
    [scanner setScanLocation:1]; // bypass '#' character
    [scanner scanHexInt:&rgbValue];
    
    return [UIColor colorWithRed:((rgbValue & 0xFF0000) >> 16)/255.0 green:((rgbValue & 0xFF00) >> 8)/255.0 blue:(rgbValue & 0xFF)/255.0 alpha:1.0];
}





- (void)loadNativeAd:(NSString *)adUnitId
{
    UIWindow *keyWindow = [[UIApplication sharedApplication] keyWindow];
    UIViewController *rootViewController = [keyWindow rootViewController];
    self.adLoader = [[GADAdLoader alloc]
                     initWithAdUnitID:adUnitId
                     rootViewController:rootViewController
                     adTypes:@[ kGADAdLoaderAdTypeUnifiedNative ]
                     options:nil];
    
    self.adLoader.delegate = self;
    GADRequest *request = [GADRequest request];
    GADMobileAds.sharedInstance.requestConfiguration.testDeviceIdentifiers = _testDevices;
    [self.adLoader loadRequest:request];
}

- (void)adLoader:(GADAdLoader *)adLoader didFailToReceiveAdWithError:(GADRequestError *)error {
    if (self.onAdFailedToLoad) {
        self.onAdFailedToLoad(@{ @"error": @{ @"message": [error localizedDescription] } });
    }
    
}



- (void)addTemplateView:(GADTTemplateView *)templateView withNativeAd:(GADUnifiedNativeAd *)nativeAd
{
    nativeAd.delegate = self;
    templateView.nativeAd = nativeAd;
    
    
    nativeAdView = templateView;
    
    [self addSubview:templateView];
    [templateView addHorizontalConstraintsToSuperviewWidth];
    [templateView addVerticalCenterConstraintToSuperview];
    
    double delayInSeconds = 0.10;
    dispatch_time_t popTime = dispatch_time(DISPATCH_TIME_NOW, delayInSeconds * NSEC_PER_SEC);
    dispatch_after(popTime, dispatch_get_main_queue(), ^(void){
        
        [self setColorForHeadlineText:primaryTextColor];
        [self setColorForDescriptionText:secondaryTextColor];
        [self setColorForAdvertiserText:tertiaryTextColor];
        [self setStylesForBackground:bgBackgroundColor arg3:bgBorderColor arg4:bgBorderWidth arg5:bgBorderRadius];
        [self setStylesForButton:btnBackgroundColor arg2:btnTextColor arg3:btnBorderColor arg4:btnBorderWidth arg5:btnBorderRadius];
        
    });
    
}

- (void)adLoader:(GADAdLoader *)adLoader didReceiveUnifiedNativeAd:(GADUnifiedNativeAd *)nativeAd {
    if (self.onAdLoaded) {
        self.onAdLoaded(@{});
    }
    
    nativeAd.delegate = self;
    
    if([_adSize  isEqual: @"medium"]) {
        GADTMediumTemplateView *templateView = [[GADTMediumTemplateView alloc] init];
        [self addTemplateView:templateView withNativeAd:nativeAd];
    } else {
        GADTSmallTemplateView *templateView = [[GADTSmallTemplateView alloc] init];
        [self addTemplateView:templateView withNativeAd:nativeAd];
    }
}

- (void)nativeAdDidRecordImpression:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if (self.onAdImpression) {
        self.onAdImpression(@{});
    }
}

- (void)nativeAdDidRecordClick:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if (self.onAdClicked) {
        self.onAdClicked(@{});
    }
}

- (void)nativeAdWillPresentScreen:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if (self.onAdOpened) {
        self.onAdOpened(@{});
    }
}

- (void)nativeAdWillDismissScreen:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if (self.onAdClosed) {
        self.onAdClosed(@{});
    }
}

- (void)nativeAdWillLeaveApplication:(nonnull GADUnifiedNativeAd *)nativeAd
{
    if(self.onAdLeftApplication) {
        self.onAdLeftApplication(@{});
    }
}



@end

