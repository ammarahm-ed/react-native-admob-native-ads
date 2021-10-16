//
//  EventEmitter.h
//  Pods
//
//  Created by Ali on 8/27/21.
//

#ifndef EventEmitter_h
#define EventEmitter_h
#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>
@interface EventEmitter: RCTEventEmitter <RCTBridgeModule>
+ (EventEmitter*)sharedInstance;
- (void)sendEvent:(NSString *) name dict:(NSDictionary *)dict;
@end

#endif /* EventEmitter_h */
