
# 使用大饼AI变声插件

本文介绍如何在你的项目中集成和使用大饼 AI 变声插件，包括 Android 和 iOS 平台。

## 技术原理

大饼 AI 变声插件是对实时 AI 声音转换 核心 API 的封装。通过[声网 SDK v4.x](https://docs.agora.io/cn/video-call-4.x/product_video_ng?platform=Android) 提供的 [setExtensionProperty](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/toc_network.html#api_irtcengine_setextensionproperty) 或 [setExtensionPropertyWithVendor](https://docs.agora.io/cn/video-call-4.x/API%20Reference/ios_ng/API/toc_network.html#api_irtcengine_setextensionproperty) 方法，传入指定的 `key` 和 `value` 参数，你可以快速集成格子互动的实时 AI 声音转换能力。

以 `setExtensionProperty` 为例，其中的 `key` 参数与格子互动的 API 名称完全对应，`value` 参数以 JSON 格式包装该 API 的部分或全部参数。因此，调用 `setExtensionProperty` 时只要传入指定的 `key` 和 `value`，就可以调用对应的格子互动 API，实现实时 AI 声音转换的有关功能。`setExtensionPropertyWithVendor` 同理。

## 前提条件

- Android 开发环境需满足以下要求：
  - Android Studio 4.1 以上版本。
  - 运行 Android 5.1 或以上版本的真机（非模拟器）。
- iOS 开发环境需满足以下要求：
  - Xcode 9.0 或以上版本。
  - 运行 iOS 12.0 或以上版本的真机（非模拟器）。

## 示例项目

| 平台    | 语言        | 示例项目                            |
| :------ | :---------- | :---------------------------------- |
| Android | Kotlin      | https://github.com/Dubbing-Voice-Engine/DubbingShengwangDemo/tree/master/Android |
| iOS     | Objective-C | https://github.com/Dubbing-Voice-Engine/DubbingShengwangDemo/tree/master/iOS     |

### 运行步骤

1. 克隆仓库：
  ```shell
	git clone https://github.com/Dubbing-Voice-Engine/DubbingShengwangDemo.git
  ```
2. 参考仓库的 `README.md` 文件完成后续步骤。

## 集成和调用流程

### 准备工作

#### 使用声网 SDK 实现视频通话

大饼AI变声插件需要与[声网视频 SDK v4.0.0](https://docs.agora.io/cn/video-call-4.x/product_live_ng?platform=Android) 搭配使用。参考以下文档集成视频 SDK v4.0.0 并实现基础的视频通话：
- [实现视频通话（Android）](https://docs.agora.io/cn/video-call-4.x/start_call_android_ng?platform=Android#%E5%BB%BA%E7%AB%8B%E9%A1%B9%E7%9B%AE)
- [实现视频通话（iOS）](https://docs.agora.io/cn/video-call-4.x/start_call_ios_ng%20?platform=iOS#%E5%88%9B%E5%BB%BA%E9%A1%B9%E7%9B%AE)

#### 集成插件

参考如下步骤在你的项目中集成大饼 AI 变声插件：

**Android**
1. 进入[声网控制台 > 云市场](https://console.agora.io/marketplace/list/all)页面，下载大饼 AI 变声插件的 Android 插件包。解压后，将所有 `.aar` 文件保存到项目文件夹的  `/app/libs`  路径。
3. 获取以下资源文件并保存到项目文件夹，比如新建 `vc_model` 目录：
  - license 文件和对应的音色文件：联系声网获取，其中音色文件的后缀名为 `.dat`。
  - 模型文件：根据[示例项目 Readme](https://github.com/Dubbing-Voice-Engine/DubbingShengwangDemo) 的说明下载所需资源。注意只需保存下图红框中的文件，其余文件无须保存：
   ![](https://web-cdn.agora.io/docs-files/1683863938704)
5. 打开 `app/build.gradle` 文件，在 `dependencies` 中添加如下行：
   ```java
   implementation fileTree(dir: "libs", include: ["*.jar", "*.aar"])
   ```

**iOS**
1. 进入[声网控制台 > 云市场](https://console.agora.io/marketplace/list/all)页面，下载大饼 AI 变声插件的 iOS 插件包。
2. 解压文件夹，将所有 `.framework` 文件导入项目，并将 **Embed** 修改为 **Embed & Sign**。
2. 获取以下资源文件并保存到项目文件夹，比如 `Resource` 路径：
  - license 文件和对应的音色文件：联系声网获取，其中音色文件的后缀名为 `.dat`。
  - 模型文件：根据[示例项目 Readme](https://github.com/Dubbing-Voice-Engine/DubbingShengwangDemo) 的说明下载所需资源。注意只需保存下图红框中的文件，其余文件无须保存：
   ![](https://web-cdn.agora.io/docs-files/1683863938704)
3. 在项目中导入 libc++ 系统库以及第三方动态库 `'MJExtension'`。

下面介绍插件相关接口的调用流程。

### 1. 启用插件

**Android**

创建并初始化 `RtcEngine` 后，首先调用 `enableExtension` 启用插件，再调用其它 API（`enableVideo`、`joinChannel` 等）。

```Kotlin
// 声明参数
private val EXTENSION_NAME = "dubbing_vc"
private val EXTENSION_VENDOR_NAME = "Dubbing"
private val EXTENSION_AUDIO_FILTER = "DubbingVC"

private val changeSpeaker_ = "changeSpeaker"
private val startRealTimeTranscribe_ = "startRealTimeTranscribe"
private val stopRealTimeTranscribe_ = "stopRealTimeTranscribe"
private val getSpeakersInfo_ = "getSpeakersInfo"

val config = RtcEngineConfig()
config.mContext = baseContext
config.mAppId = appId
config.mEventHandler = mRtcEventHandler
// 加载插件
config.addExtension(EXTENSION_NAME)
config.mExtensionObserver = extensionObserver
// 创建并初始化 RtcEngine
mRtcEngine = RtcEngine.create(config)
mRtcEngine.enableAudio()
// 启用插件
mRtcEngine.enableExtension(EXTENSION_VENDOR_NAME, EXTENSION_AUDIO_FILTER, enable)
```

**iOS**

创建并初始化 `AgoraRtcEngineKit` 后，首先调用 `enableExtensionWithVendor` 启用插件，再调用其它 API（`enableVideo`、`joinChannelByToken` 等）。

```objective-c
AgoraRtcEngineConfig *cfg = [[AgoraRtcEngineConfig alloc] init];
cfg.appId = appId;
cfg.eventDelegate = self;
cfg.channelProfile = AgoraChannelProfileLiveBroadcasting;
cfg.audioScenario = AgoraAudioScenarioGameStreaming;
self.agoraKit = [AgoraRtcEngineKit sharedEngineWithConfig:cfg delegate:self];
[self.agoraKit setClientRole:AgoraClientRoleBroadcaster];

[self.agoraKit enableExtensionWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" enabled:YES];
```

### 2. 设置资源文件路径

在集成插件时，你已经将 license 文件、音色文件、模型文件保存在指定目录。这一步只需要传入这些资源文件所在路径：

**Android**
```Kotlin
val modelPath: String = "${context.filesDir}${File.separator}vc_model"
```

**iOS**
```objective-c
NSString *sourcePath = [[NSBundle mainBundle] resourcePath];
[self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"setResourcesFilePath" value:sourcePath];
```

### 3. 获取音色列表

收到声网 SDK 的 `onExtensionStarted` 回调后，调用 `getExtensionProperty`（Android）或 `getExtensionPropertyWithVendor`（iOS），传入 key 为 `getSpeakersInfo`，获取音色列表：

**Android**
```Kotlin
val speakerList = mRtcEngine.getExtensionProperty(
    EXTENSION_VENDOR_NAME,
    EXTENSION_AUDIO_FILTER,
    getSpeakersInfo_
)
// 将 JSON 转换为数组
val arr = JSONArray(speakerList)
```

**iOS**
```objective-c
NSString *voices = [self.agoraKit getExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"getSpeakersInfo"];
// 将 JSON 转换为数组
NSData *nsData = [voices dataUsingEncoding:NSUTF8StringEncoding];
_speakerArray = [NSJSONSerialization JSONObjectWithData:nsData options:kNilOptions error:nil];
```

音色列表以 JSON 数据返回，你需要自行解析。

### 4. 开始变声

调用 `setExtensionProperty`（Android）或 `setExtensionPropertyWithVendor`（iOS）并传入对应的 key 和 value。

**Android**
```Kotlin
mRtcEngine.setExtensionProperty(
    EXTENSION_VENDOR_NAME,
    EXTENSION_AUDIO_FILTER,
    startRealTimeTranscribe_,
    "true"
)
```

**iOS**
```objective-c
[self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"startRealTimeTranscribe" value:@"startRealTimeTranscribe"];
```

### 5. 选择音色
传入第 3 步获取的音色列表中的音色 ID，即可设置成对应的音色：

**Android**
```Kotlin
mRtcEngine.setExtensionProperty(
    EXTENSION_VENDOR_NAME,
    EXTENSION_AUDIO_FILTER,
    changeSpeaker_,
    id
)
```

```objective-c
[self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"changeSpeaker" value:@“”];
```

### 6. 停止变声

**Android**
```Kotlin
mRtcEngine.setExtensionProperty(
    EXTENSION_VENDOR_NAME,
    EXTENSION_AUDIO_FILTER,
    stopRealTimeTranscribe_,
    "true"
)
```

**iOS**
```objective-c
[self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"stopRealTimeTranscribe" value:@"stopRealTimeTranscribe"];
```

### 7. 释放资源
关闭插件，释放插件所占用的资源。

**Android**
```Kotlin
mRtcEngine.enableExtension(EXTENSION_VENDOR_NAME, EXTENSION_AUDIO_FILTER, false)
```

**iOS**
```objective-c
[self.agoraKit enableExtensionWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" enabled:NO];
```

## 更多参考

### 接口说明

插件所有相关接口的参数解释详见[接口说明](./api_dubbing)。
