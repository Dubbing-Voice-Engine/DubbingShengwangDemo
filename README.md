# DubbingShengwangDemo
声网云市场插件-大饼变声SDK

### Android

准备工作
* 在此链接下载所需资源：https://v.dubbing.tech/vc-plug/resources.zip
* 获取你的声网appId、userId、声网token、房间id，填入MainActivity对应变量。
* 运行demo，此时设备上会生成应用目录。
* 通过Android Studio的DeviceFileExplorer，找到data/data/com.gerzz.demo/files目录，并在此目录下新建vc_model目录。
* 获取你的license文件、音色文件、模型文件。将这些文件放入上一步创建的文件夹内。
* 用另外一台设备进入房间内。

界面操作
1. 点击进入房间，进入成功后，另外一台设备会听到你的声音。
2. 点击`开始变声`，此时会初始化变声引擎，成功后就开始变声。注：此时只能听到原声。
3. 点击`设置音色`，会出现音色列表。点击一个音色，设置音色成功后，另外一台设备就可以听到变化之后的声音。
4. 点击`停止变声`，则听到原声。
5. 点击`禁用插件`后，则听到原声。
6. 禁用插件后，如需再次开启，则需先点击`启用插件`，然后再从第2步开始操作即可。

### iOS

准备工作
* 在此链接下载所需资源：https://v.dubbing.tech/vc-plug/resources.zip * 导入项目DBDemo中
* pod install
* 在ViewController中填写你的appId、uid、channelId，声网token
* 项目导入libc++库
* 真机运行项目

界面操作
1. 启动 app，你可以在界面上看到 `开启声音引擎` 和 `选择音色` 按钮
2. 点击 `开启声音引擎` 。
3. 点击 `选择音色` 选择音色。

