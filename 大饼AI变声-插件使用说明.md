----------------------------------------------模版使用步骤--------------------------------------------------------

1. 在 Markdown 编辑器里打开模版，推荐 Typora、VSCode。
2. 先搜索“”，全部替换成你的插件名称。
3. 再搜索“TODO”，按照对应说明依次修改。**修改后注意不要保留括号**。
4. 检查全文内容和样式。注意没有标 TODO 的内容由声网维护，**建议不要随意修改**。模版样式的最终效果参见[示例文档](https://docs.agora.io/cn/extension_customer/quickstart_faceunity?platform=Android)。

----------------------------------------------以下为模版正文-----------------------------------------------------

# 使用大饼AI变声插件

本文介绍如何在你的项目中集成和使用大饼AI变声插件，包括 Android 和 iOS 平台。

## 技术原理

大饼AI变声插件是对实时AI声音转换核心 SDK 的封装。通过调用[声网视频 SDK v4.0.0](https://docs.agora.io/cn/video-call-4.x/product_video_ng?platform=Android) 的 [setExtensionProperty](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/toc_network.html#api_irtcengine_setextensionproperty) 或 [setExtensionPropertyWithVendor](https://docs.agora.io/cn/video-call-4.x/API%20Reference/ios_ng/API/toc_network.html#api_irtcengine_setextensionproperty)方法，传入指定的 `key` 和 `value` 参数，你可以快速集成实时AI声音转换。支持的 key 详见[插件的 key 概览]()。

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

**Android**

1. 克隆仓库：
  ```shell
	git clone https://github.com/Dubbing-Voice-Engine/DubbingShengwangDemo.git
  ```
2. 在 Android Studio 中打开示例项目 `Android`。
3. 将项目与 Gradle 文件同步。
4. 打开 `Android/app/src/main/java/com/gerzz/demo/MainActivity.kt`，进行如下修改：
	- 将 `appId` 替换为你的 App ID。获取 App ID 请参考[开始使用 Agora 平台](https://docs.agora.io/cn/Agora%20Platform/get_appid_token?platform=All%20Platforms)。
	- 将 `user` 和 `token` 和 `room` 分别替换为你的 `用户ID` 和 `声网token` 和 `房间ID`。
5. 连接一台 Android 真机（非模拟器），运行项目。
6. 通过Android Studio的DeviceFileExplorer，找到 `data/data/com.gerzz.demo/files` 目录，并在此目录下新建 `vc_model` 目录。
7. 获取你的license文件、音色文件、模型文件。将这些文件放入上一步创建的文件夹内。

**iOS**

1. 克隆仓库：
  ```shell
	git clone https://github.com/Dubbing-Voice-Engine/DubbingShengwangDemo/tree/master/iOS
  ```
2. 进入[声网控制台 > 云市场](https://console.agora.io/marketplace/list/all)页面，下载大饼AI变声插件的 iOS 插件包。解压后，将所有文件保存本地，或者实现自定义下载资源包 。
3. 在终端中进入项目，运行以下命令使用 CocoaPods 安装依赖：
  ```
	pod install
  ```
3. 在 Xcode 中打开项目`。
4. 打开ViewController`，进行如下修改：

  NSString * appId = @"";
  NSString * token = @"";
  NSString * channelId = @"";
  int uid = ;
  ```
5. 连接一台 iOS 真机（非模拟器），运行项目。

### 预期效果

运行成功后，示例项目会安装到你的 Android 或 iOS 设备上。

**iOS**
1. 启动 app，你可以在界面上看到 `开启声音引擎` 和 `选择音色` 按钮
2. 点击 `开启声音引擎` 。
3. 点击 `选择音色` 选择音色。

**Android**
1. 启动 app，你可以在界面上看到 `进入房间` 和 `离开房间` 和 `开始变声` 和 `停止变声` 和 `启用插件` 和 `禁用插件` 和 `设置音色` 按钮
2. 点击 `进入房间` 。
3. 点击 `开始变声` 。
4. 点击 `设置音色` 。
5. 出现的音色列表中，选择一个点击。

## 集成和调用流程

### 准备工作

#### 使用声网 SDK 实现视频通话

大饼AI变声插件需要与[声网视频 SDK v4.0.0](https://docs.agora.io/cn/video-call-4.x/product_live_ng?platform=Android) 搭配使用。参考以下文档集成视频 SDK v4.0.0 并实现基础的视频通话：
- [实现视频通话（Android）](https://docs.agora.io/cn/video-call-4.x/start_call_android_ng?platform=Android#%E5%BB%BA%E7%AB%8B%E9%A1%B9%E7%9B%AE)
- [实现视频通话（iOS）](https://docs.agora.io/cn/video-call-4.x/start_call_ios_ng%20?platform=iOS#%E5%88%9B%E5%BB%BA%E9%A1%B9%E7%9B%AE)

#### 集成插件

参考如下步骤在你的项目中集成大饼AI变声插件：

**Android**
1. 在[声网云市场下载](https://docs.agora.io/cn/extension_customer/downloads?platform=All%20Platforms)页面下载大饼AI变声插件的 Android 插件包。解压后，将所有 `.aar` 文件保存到项目文件夹的  `/app/libs`  路径。
2. 通过Android Studio的DeviceFileExplorer，找到 `data/data/com.gerzz.demo/files` 目录，并在此目录下新建 `vc_model` 目录。
3. 获取你的license文件、音色文件、模型文件。将这些文件放入上一步创建的文件夹内。
4. 打开 `app/build.gradle` 文件，在 `dependencies` 中添加如下行：
   ```java
   implementation fileTree(dir: "libs", include: ["*.jar", "*.aar"])
   ```
### 1. 声明参数
  ```Kotlin
            private val EXTENSION_NAME = "dubbing_vc"
            private val EXTENSION_VENDOR_NAME = "Dubbing"
            private val EXTENSION_AUDIO_FILTER = "DubbingVC"

            private val changeSpeaker_ = "changeSpeaker"
            private val starRealTimeTranscribe_ = "starRealTimeTranscribe"
            private val stopRealTimeTranscribe_ = "stopRealTimeTranscribe"
            private val getSpeakersInfo_ = "getSpeakersInfo"
  ```
### 2. 启用插件
  ```Kotlin
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            config.addExtension(EXTENSION_NAME)
            config.mExtensionObserver = extensionObserver
            mRtcEngine = RtcEngine.create(config)
            mRtcEngine.enableAudio()
  ```
### 3. 准备引擎资源
          获取license文件、音色文件、模型文件。通过以下代码获取资源文件夹。
          ```Kotlin
          val modelPath: String = "${context.filesDir}${File.separator}vc_model"
          ```
          下载资源文件并放入此目录。
### 4. 获取音色列表
  ```Kotlin
      val speakerList = mRtcEngine.getExtensionProperty(
                    EXTENSION_VENDOR_NAME,
                    EXTENSION_AUDIO_FILTER,
                    getSpeakersInfo_
                )
  ```
  此返回值是Json数据，需自行解析。
### 5. 开始变声
  ```Kotlin
      mRtcEngine.setExtensionProperty(
            EXTENSION_VENDOR_NAME,
            EXTENSION_AUDIO_FILTER,
            starRealTimeTranscribe_,
            "true"
        )
  ```
### 6. 设置音色
  将第4步中获取的音色列表中元素的id传入
  ```Kotlin
      mRtcEngine.setExtensionProperty(
            EXTENSION_VENDOR_NAME,
            EXTENSION_AUDIO_FILTER,
            changeSpeaker_,
            id
        )
  ```
### 7. 停止变声
  ```Kotlin
      mRtcEngine.setExtensionProperty(
            EXTENSION_VENDOR_NAME,
            EXTENSION_AUDIO_FILTER,
            stopRealTimeTranscribe_,
            "true"
        )
  ```
### 8. 释放资源
  ```Kotlin
      mRtcEngine.enableExtension(EXTENSION_VENDOR_NAME, EXTENSION_AUDIO_FILTER, false)
  ```

**iOS**
1. 在[声网云市场下载](https://docs.agora.io/cn/extension_customer/downloads?platform=All%20Platforms)页面下载大饼AI变声插件的 iOS 插件包。解压后，将所有资源包 文件保存到你的项目文件夹下。
2. 项目需导入libc++系统库以及第三方pod 'MJExtension'

### 1. 启用插件

    AgoraRtcEngineConfig *cfg = [[AgoraRtcEngineConfig alloc] init];
    cfg.appId = appId;
    cfg.eventDelegate = self;
    cfg.channelProfile = AgoraChannelProfileLiveBroadcasting;
    cfg.audioScenario = AgoraAudioScenarioGameStreaming;
    self.agoraKit = [AgoraRtcEngineKit sharedEngineWithConfig:cfg delegate:self];
    [self.agoraKit setClientRole:AgoraClientRoleBroadcaster];
    
    [self.agoraKit enableExtensionWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" enabled:YES];

### 2. 设置资源文件路径

    NSString *sourcePath = [[NSBundle mainBundle] resourcePath];
   [self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"setResourcesFilePath" value:sourcePath];
   
### 3. 获取声音引擎音色

    NSString *voices = [self.agoraKit getExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"getSpeakersInfo"];
    NSData *nsData = [voices dataUsingEncoding:NSUTF8StringEncoding];
    _speakerArray = [NSJSONSerialization JSONObjectWithData:nsData options:kNilOptions error:nil];

### 4. 启动声音引擎

        [self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"starRealTimeTranscribe" value:@"starRealTimeTranscribe"];

### 5. 选择音色

    [self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"changeSpeaker" value:@“”];

### 6. 关闭声音引擎

        [self.agoraKit setExtensionPropertyWithVendor:[EngineFilterManager vendorName] extension:@"RealTimeTranscribe" key:@"stopRealTimeTranscribe" value:@"stopRealTimeTranscribe"];

## 更多参考

### 接口说明

插件所有相关接口的参数解释详见[接口说明]()。

