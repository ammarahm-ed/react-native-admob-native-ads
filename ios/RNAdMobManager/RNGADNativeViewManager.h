@import React;


@interface RNGADNativeViewManager : RCTViewManager
+ (NSString*)  EVENT_AD_FAILED_TO_LOAD;
+ (NSString*)  EVENT_AD_CLICKED;
+ (NSString*)  EVENT_AD_CLOSED;
+ (NSString*)  EVENT_AD_OPENED;
+ (NSString*)  EVENT_AD_IMPRESSION;
+ (NSString*)  EVENT_AD_LOADED;
+ (NSString*)  EVENT_AD_LEFT_APPLICATION ;
+ (NSString*)  EVENT_UNIFIED_NATIVE_AD_LOADED;
@end
