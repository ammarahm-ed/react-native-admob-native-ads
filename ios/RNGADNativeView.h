#import <React/RCTView.h>

@import GoogleMobileAds;

@interface RNGADNativeView : RCTView <GADUnifiedNativeAdLoaderDelegate,
    GADUnifiedNativeAdDelegate>

@property(nonatomic, strong) GADUnifiedNativeAd *nativeAdView;
@property(nonatomic, strong) GADAdLoader *adLoader;

@property (nonatomic, copy) NSArray *testDevices;
@property (nonatomic, copy) NSString *adSize;

@property (nonatomic, copy) NSString *headlineTextColor;
@property (nonatomic, copy) NSString *descriptionTextColor;
@property (nonatomic, copy) NSString *advertiserTextColor;
@property (nonatomic, copy) NSDictionary *buttonStyle;
@property (nonatomic, copy) NSDictionary *backgroundStyle;


@property (nonatomic, copy) RCTBubblingEventBlock onAdLoaded;
@property (nonatomic, copy) RCTBubblingEventBlock onAdFailedToLoad;
@property (nonatomic, copy) RCTBubblingEventBlock onAdOpened;
@property (nonatomic, copy) RCTBubblingEventBlock onAdClosed;
@property (nonatomic, copy) RCTBubblingEventBlock onAdLeftApplication;
@property (nonatomic, copy) RCTBubblingEventBlock onAdClicked;
@property (nonatomic, copy) RCTBubblingEventBlock onAdImpression;

- (void)loadNativeAd:(NSString *)adUnitId;

@end
