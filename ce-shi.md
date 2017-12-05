正在测试

···

```
defaultConfig
{
applicationId "com.example.gsyvideoplayer"
minSdkVersion globalConfiguration.androidMinSdkVersion
targetSdkVersion globalConfiguration.androidTargetSdkVersion

versionCode 3
versionName "1.4.9"

ndk{
//设置支持的SO库架构
abiFilters 'armeabi','armeabi-v7a','x86'
}
}
```

···

