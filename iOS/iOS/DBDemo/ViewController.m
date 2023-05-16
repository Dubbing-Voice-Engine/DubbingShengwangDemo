//
//  ViewController.m
//  DBDome
//
//  Created by 杜改革 on 2023/4/20.
//

#import "ViewController.h"
#import <AgoraRtcKit/AgoraRtcKit.h>
#import <DubbingRtvcFilter/EngineFilterManager.h>

NSString * appId = @"3bc48e73d34d4617b3a48a3a2db3504e";
NSString * token = @"0063bc48e73d34d4617b3a48a3a2db3504eIACVZdWppa8qVPDsg1TpJggn507rOolNmbexLLSAZO/dV6TPDnrhY8/4HAAm6/oEKUpDZAMAAQApSkNkAgApSkNkBAApSkNk";
NSString * channelId = @"WB6Op";
int uid = 201520;

@interface ViewController ()<AgoraMediaFilterEventDelegate, AgoraRtcEngineDelegate>
@property (strong, nonatomic) AgoraRtcEngineKit *agoraKit;
@property (weak, nonatomic) IBOutlet UIButton *enginebtn;
@property (weak, nonatomic) IBOutlet UIButton *speakerbtn;
@property (assign, nonatomic) BOOL isStarEngine;
@property (strong, nonatomic) NSString * currSpeakerId;
@property (strong, nonatomic) NSString * currSpeakerName;
@property (strong, nonatomic) NSArray * speakerArray;
@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
   
    AgoraRtcEngineConfig *cfg = [[AgoraRtcEngineConfig alloc] init];
    cfg.appId = appId;
    cfg.eventDelegate = self;
    cfg.channelProfile = AgoraChannelProfileLiveBroadcasting;
    cfg.audioScenario = AgoraAudioScenarioGameStreaming;
    self.agoraKit = [AgoraRtcEngineKit sharedEngineWithConfig:cfg delegate:self];
    [self.agoraKit setClientRole:AgoraClientRoleBroadcaster];
    
    [self.agoraKit enableExtensionWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" enabled:YES];
    
    AgoraRtcChannelMediaOptions* options = [[AgoraRtcChannelMediaOptions alloc] init];
    options.publishCameraTrack = false;
    options.publishMicrophoneTrack = true;
    [self.agoraKit joinChannelByToken:token channelId:channelId uid:uid mediaOptions:options joinSuccess:^(NSString * _Nonnull channel, NSUInteger uid, NSInteger elapsed) {
          NSLog(@"success");
      }];
    NSString *sourcePath = [[NSBundle mainBundle] resourcePath];
   [self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"setResourcesFilePath" value:sourcePath];
    NSString *voices = [self.agoraKit getExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"getSpeakersInfo"];
    NSData *nsData = [voices dataUsingEncoding:NSUTF8StringEncoding];
    _speakerArray = [NSJSONSerialization JSONObjectWithData:nsData options:kNilOptions error:nil];
    NSLog(@"sss-----------%@", voices);
    NSLog(@"sss-----------%@", _speakerArray);
}

- (void)rtcEngine:(AgoraRtcEngineKit * _Nonnull)engine didOccurError:(AgoraErrorCode)errorCode {
    NSLog(@"-----------%ld", (long)errorCode);
}

- (IBAction)engineClick:(id)sender {
    if (_isStarEngine) {
     [self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"stopRealTimeTranscribe" value:@"stopRealTimeTranscribe"];
        [self.enginebtn setTitle:@"开启声音引擎" forState:0];
        [self.speakerbtn setTitle:@"选择音色" forState: 0];
    } else {
        [self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"startRealTimeTranscribe" value:@"startRealTimeTranscribe"];
        [self.enginebtn setTitle:@"关闭声音引擎" forState:0];
    }
    _isStarEngine = !_isStarEngine;
}

- (IBAction)speakerClick:(id)sender {
    if (_isStarEngine == false) {
        return;
    }
    UIAlertController * alert = [UIAlertController alertControllerWithTitle:@"选择音色" message:@"开始选择符合你的音色吧" preferredStyle:UIAlertControllerStyleActionSheet];
    for (NSDictionary * dict in _speakerArray) {
        UIAlertAction * action = [UIAlertAction actionWithTitle:[NSString stringWithFormat:@"%@", dict[@"name"]] style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            self.currSpeakerId = [NSString stringWithFormat:@"%@", dict[@"speakerModelId"]];
            self.currSpeakerName = [NSString stringWithFormat:@"%@", dict[@"name"]];
            [self changeClick];
        }];
        [alert addAction:action];
    }
    UIAlertAction * action = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
    }];
    [alert addAction:action];
    [self presentViewController:alert animated:YES completion:nil];
}

- (void)changeClick{
    int ret = [self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"changeSpeaker" value:_currSpeakerId];
    NSLog(@"setExtensionPropertyWithVendor -> %d", ret);
    [self.speakerbtn setTitle:_currSpeakerName forState: 0];
}
@end
