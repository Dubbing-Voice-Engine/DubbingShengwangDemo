//
//  EngineFilterManager.h
//  MyFuckingPowerFilter
//
//  Created by 杜改革 on 2023/4/12.
//

#import <Foundation/Foundation.h>
#import <AgoraRtcKit/AgoraMediaFilterEventDelegate.h>

NS_ASSUME_NONNULL_BEGIN

@interface EngineFilterManager : NSObject<AgoraMediaFilterEventDelegate>
+ (instancetype)sharedInstance;

+ (NSString * __nonnull)vendorName;

@end

NS_ASSUME_NONNULL_END

