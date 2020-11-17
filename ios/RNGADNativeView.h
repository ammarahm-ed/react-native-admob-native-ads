#import <React/RCTView.h>
#import <React/RCTBridge.h>

@import GoogleMobileAds;

@interface RNGADNativeView : GADUnifiedNativeAdView <GADUnifiedNativeAdLoaderDelegate,
    GADUnifiedNativeAdDelegate>

@property(nonatomic, strong) GADUnifiedNativeAd *nativeAdView;
@property(nonatomic, strong) GADAdLoader *adLoader;

@property (nonatomic, copy) NSArray *testDevices;
@property (nonatomic, copy) NSNumber *refreshInterval;
@property (nonatomic, copy) NSString *adUnitID;
@property (nonatomic, copy) NSNumber *delayAdLoad;
@property (nonatomic, copy) NSNumber *headline;
@property (nonatomic, copy) NSNumber *tagline;
@property (nonatomic, copy) NSNumber *advertiser;
@property (nonatomic, copy) NSNumber *store;
@property (nonatomic, copy) NSNumber *price;
@property (nonatomic, copy) NSNumber *image;
@property (nonatomic, copy) NSNumber *icon;
@property (nonatomic, copy) NSNumber *mediaview;
@property (nonatomic, copy) NSNumber *starrating;
@property (nonatomic, copy) NSNumber *callToAction;
@property (nonatomic, copy) NSNumber *adChoicesPlacement;
@property (nonatomic) BOOL *requestNonPersonalizedAdsOnly; 

- (instancetype)initWithBridge:(RCTBridge *)bridge;

@property (nonatomic, copy) RCTBubblingEventBlock onAdLoaded;
@property (nonatomic, copy) RCTBubblingEventBlock onAdFailedToLoad;
@property (nonatomic, copy) RCTBubblingEventBlock onAdOpened;
@property (nonatomic, copy) RCTBubblingEventBlock onAdClosed;
@property (nonatomic, copy) RCTBubblingEventBlock onAdLeftApplication;
@property (nonatomic, copy) RCTBubblingEventBlock onAdClicked;
@property (nonatomic, copy) RCTBubblingEventBlock onAdImpression;
@property (nonatomic, copy) RCTBubblingEventBlock onUnifiedNativeAdLoaded;


@end
