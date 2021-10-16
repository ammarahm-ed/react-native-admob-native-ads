//
//  comparable.h
//  Pods
//
//  Created by Ali on 8/25/21.
//

#ifndef comparable_h
#define comparable_h
#import <Foundation/Foundation.h>


//NOTE: Class must check to make sure it is the same class as whatever is passed in
@protocol Comparable

- (int)compareTo:(id<Comparable, NSObject>)object;
- (BOOL)isEqual:(id<Comparable, NSObject>)object;

@end
#endif /* comparable_h */
