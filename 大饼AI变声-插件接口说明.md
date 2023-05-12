## 声网 SDK 的 API 参考

本节提供声网 SDK 中插件相关 API 的参考文档。

### Android

- `RtcEngineConfig` 类的 [addExtension](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/api_irtcengine_addextension.html)
- `RtcEngine` 类的 [enableExtension](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/toc_network.html#api_irtcengine_enableextension)
- `RtcEngine` 类的 [setExtensionProperty](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/toc_network.html#api_irtcengine_setextensionproperty)
- `RtcEngine` 类的 [getExtensionProperty](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/toc_network.html#api_irtcengine_getextensionproperty)
- `IMediaExtensionObserver` 类的 [onEvent](https://docs.agora.io/cn/video-call-4.x/API%20Reference/java_ng/API/toc_network.html#callback_irtcengineeventhandler_onextensionevent)

### iOS

- `AgoraRtcEngineKit` 类的 [enableExtensionWithVendor](https://docs.agora.io/cn/video-call-4.x/API%20Reference/ios_ng/API/toc_network.html#api_irtcengine_enableextension)
- `AgoraRtcEngineKit` 类的 [setExtensionPropertyWithVendor](https://docs.agora.io/cn/video-call-4.x/API%20Reference/ios_ng/API/toc_network.html#api_irtcengine_setextensionproperty)
- `AgoraRtcEngineKit` 类的 [getExtensionPropertyWithVendor](https://docs.agora.io/cn/video-call-4.x/API%20Reference/ios_ng/API/toc_network.html#api_irtcengine_getextensionproperty)
- `AgoraMediaFilterEventDelegate` 类的 [onEvent](https://docs.agora.io/cn/video-call-4.x/API%20Reference/ios_ng/API/toc_network.html#callback_irtcengineeventhandler_onextensionevent)

## 插件的 key 概览 <a name="key-value"></a>

在声网 SDK 中调用插件相关 API 时，需要传入指定的 key 和 value。本节介绍大饼 AI 变声插件支持的所有 key。

### 方法 key
调用声网 SDK 的 `setExtensionProperty`/`setExtensionPropertyWithVendor` 方法时，支持传入以下 key：

| setExtensionProperty/setExtensionPropertyWithVendor 方法的 key| 描述 |
| ------------------------------------ | -------- |
| [setResourcesFilePath](#setresourcesfilepath)（仅 `setExtensionPropertyWithVendor` 方法支持）| 设置资源文件路径。 |
| [startRealTimeTranscribe](#startRealTimeTranscribe)| 开启声音引擎。 |
| [stopRealTimeTranscribe](#stoprealtimetranscribe)| 关闭声音引擎。 |
| [changeSpeaker](#changespeaker)| 切换音色。 |

调用声网 SDK 的 `getExtensionProperty`/`getExtensionPropertyWithVendor` 方法时，支持传入以下 key：

| getExtensionProperty 方法的 key| 描述 |
| ------------------------------------ | -------- |
| [getSpeakersInfo](#getspeakersinfo)| 获取音色列表。 |

### 回调 key
声网 SDK 的 `onEvent` 回调可能包括以下 key：

|onEvent 回调的 key| 描述 |
| ---------------------------- | -------------- |
| [startRealTimeTranscribe](#startRealTimeTranscribe-event)| 返回对应方法 key 的调用结果。 |
| [stopRealTimeTranscribe](#stoprealtimetranscribe-event)|  返回对应方法 key 的调用结果。 |
| [changeSpeaker](#changespeaker-event)| 返回对应方法 key 的调用结果。 |

##  方法 key 的 value 说明

### setResourcesFilePath

value 参数：String 型，资源文件的路径。

### startRealTimeTranscribe

value 参数：设为 `"startRealTimeTranscribe"`，表示开始变声。

### changeSpeaker

value 参数：String 型，音色 ID。

### stopRealTimeTranscribe

value 参数：设为 `"stopRealTimeTranscribe"`，表示停止变声。

### getSpeakersInfo

value 参数：无。

传入该 key 后，音色列表以 JSON 的数据形式返回。

##  回调 key 的 value 说明

### startRealTimeTranscribe <a name="startRealTimeTranscribe-event"></a>

value 可能为以下参数：

| value 参数| 描述 |
| ----------------- | ----------------------- |
| `success` | String 型，引擎启动成功。|
| `license error` | String 型，license 文件鉴权失败。|
| `speaker info error` | String 型，音色文件错误。|
| `file not found` | String 型，找不到资源文件，如 license 文件、音色文件、模型文件。|
| `fail` | String 型，引擎启动失败。|
| `dir not found or package name not found` | String 型，资源文件目录或者应用包名获取失败。|

### stopRealTimeTranscribe <a name="stoprealtimetranscribe-event"></a>

value 可能为以下参数：

| value 参数| 描述 |
| ----------------- | ----------------------- |
| `success` | String 型，停止变声成功。|

### changeSpeaker <a name="changespeaker-event"></a>

value 可能为以下参数：

| value 参数| 描述 |
| ----------------- | ----------------------- |
| `speaker not found` | String 型，找不到音色文件。|
| `fail` | String 型，设置音色失败。|
| `success` | String 型，设置音色成功。|

## 错误码
调用声网 SDK 的插件相关 API 时，可能返回以下错误码：
- `10001`：无法找到资源文件。
