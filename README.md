# AdSdk 

[![Download](https://api.bintray.com/packages/zhangkari/maven/adsdk/images/download.svg) ](https://bintray.com/zhangkari/maven/adsdk/_latestVersion)

**AdSdk** 同时支持Admob & Facebook 广告平台  
任何问题或建议欢迎联系我 (zhangkaric@gmail.com).

## 使用方法:
``` java
implementation 'com.tomtom.sdk.ad:adsdk:1.0.1'

SuperAdSdk.init(context, path);
SuperAdSdk.getInstance().showSplashAd();
SuperAdSdk.getInstance().showBannerAd();

```
## 支持广告平台:   
- **Admob**
- **Facebook**

## 支持广告类型:  
- **Admob 插屏广告** 
- **Admob banner广告** 

## 依赖:   
- **com.google.android.gms:play-services-ads:19.5.0**
- **com.facebook.android:audience-network-sdk:6.2.0**
