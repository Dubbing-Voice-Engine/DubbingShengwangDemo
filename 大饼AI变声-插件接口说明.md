
## 声网 SDK 的 API 参考

本节提供声网 SDK 中与使用插件相关的 API 参考。

### Java

- `RtcEngineConfig` 类的 [addExtension](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/api_irtcengine_addextension.html)
- `RtcEngine` 类的 [enableExtension](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/toc_network.html#api_irtcengine_enableextension)
- `RtcEngine` 类的 [setExtensionProperty](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/toc_network.html#api_irtcengine_setextensionproperty)
- `IMediaExtensionObserver` 类的 [onEvent](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/toc_network.html#callback_irtcengineeventhandler_onextensionevent)

### Objective-C

- `AgoraRtcEngineKit` 类的 [enableExtensionWithVendor](https://docs.agora.io/cn/video-call-4.x/API%20Reference/ios_ng/API/toc_network.html#api_irtcengine_enableextension)
- `AgoraRtcEngineKit` 类的 [setExtensionPropertyWithVendor](https://docs.agora.io/cn/video-call-4.x/API%20Reference/ios_ng/API/toc_network.html#api_irtcengine_setextensionproperty)
- `AgoraMediaFilterEventDelegate` 类的 [onEvent](https://docs.agora.io/cn/video-call-4.x/API%20Reference/ios_ng/API/toc_network.html#callback_irtcengineeventhandler_onextensionevent)

## 插件的 key 概览 <a name="key-value"></a>

在声网 SDK 中调用插件相关 API 时，需要传入指定的 key 和 value。本节介绍大饼AI变声支持的所有 key。

**iOS**
### 方法 key
调用声网 SDK 的 `setExtensionProperty`/`setExtensionPropertyWithVendor` 方法时，支持传入以下 key：
1：setResourcesFilePath：设置资源文件路径
2：starRealTimeTranscribe：开启声音引擎
3：changeSpeaker：切换音色
4：stopRealTimeTranscribe：关闭声音引擎

### 回调 key
调用声网 SDK 的 `getExtensionProperty`/`getExtensionPropertyWithVendor` 方法时，支持传入以下 key：
1：getSpeakersInfo：获取音色数据

##  方法 key 的 value 说明
key：setResourcesFilePath    value：资源文件路径（string）

key：starRealTimeTranscribe    value：“starRealTimeTranscribe”

key：changeSpeaker    value：音质id（string）

key：stopRealTimeTranscribe    value：“starRealTimeTranscribe”

key：getSpeakersInfo    value：音色json字符串，转换后位数组
                 
## 错误码
code: 10001 无法找到资源文件

**Android**

### 方法 key
调用声网 SDK 的 `setExtensionProperty` 方法时，支持传入以下 key：
| setExtensionProperty/setExtensionPropertyWithVendor 方法的 key| 描述 |
| ------------------------------------ | -------- |
| [starRealTimeTranscribe](#starrealtimetranscribe)| 开启声音引擎 |
| [stopRealTimeTranscribe](#stoprealtimetranscribe)| 关闭声音引擎 |
| [changeSpeaker](#changespeaker)| 设置音色 |

调用声网 SDK 的 `getExtensionProperty` 方法时，支持传入以下 key：
| getExtensionProperty 方法的 key| 描述 |
| ------------------------------------ | -------- |
| [getSpeakersInfo](#getspeakersinfo)| 获取音色列表,返回值String型。返回值内容是Json，需自行解析。 |

### 回调 key
声网 SDK 的 `onEvent` 回调可能包括以下 key：

|onEvent 回调的 key| 描述 |
| ---------------------------- | -------------- |
| [starRealTimeTranscribe](#starrealtimetranscribe)| value可能包括：`Success` `license error` `speaker info error` `file not found` `fail` `dir not found or package name not found` |
| [stopRealTimeTranscribe](#stoprealtimetranscribe)|  value可能包括：`Success` |
| [changeSpeaker](#changespeaker)| value可能包括：`Speaker not found` `Fail` `Success` |

##  方法 key 的 value 说明

### starRealTimeTranscribe

value 包含以下参数：

| value 参数| 描述 |
| ----------------- | ----------------------- |
| `Success` | String 型，引擎启动成功|
| `license error` | String 型，license文件鉴权失败|
| `speaker info error` | String 型，音色文件错误|
| `file not found` | String 型，找不到资源文件，如license文件、音色文件、模型文件|
| `fail` | String 型，启动失败|
| `dir not found or package name not found` | String 型，资源文件目录或者应用包名获取失败|

### starRealTimeTranscribe

value 包含以下参数：

| value 参数| 描述 |
| ----------------- | ----------------------- |
| `Success` | String 型，停止变声成功|

### changeSpeaker

value 包含以下参数：

| value 参数| 描述 |
| ----------------- | ----------------------- |
| `Speaker not found` | String 型，找不到音色文件|
| `Fail` | String 型，设置音色失败|
| `Success` | String 型，设置音色成功|

### 方法 key
调用声网 SDK 的 `setExtensionProperty` 方法时，支持传入以下 key：
| setExtensionProperty/setExtensionPropertyWithVendor 方法的 key| 描述 |
| ------------------------------------ | -------- |
| [starRealTimeTranscribe](#starrealtimetranscribe)| 开启声音引擎 |
| [stopRealTimeTranscribe](#stoprealtimetranscribe)| 关闭声音引擎 |
| [changeSpeaker](#changespeaker)| 设置音色 |

调用声网 SDK 的 `getExtensionProperty` 方法时，支持传入以下 key：
| getExtensionProperty 方法的 key| 描述 |
| ------------------------------------ | -------- |
| [getSpeakersInfo](#getspeakersinfo)| 获取音色列表 |
