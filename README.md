# MAHAndroidUpdater - <a href="https://play.google.com/store/apps/developer?id=Sattar+Hummatli+-+MobAppHome">MobAppHome</a>  android update helper library
[ ![Download](https://api.bintray.com/packages/hummatli/maven/mah-android-updater/images/download.svg) ](https://bintray.com/hummatli/maven/mah-android-updater/_latestVersion) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MAHAndroidUpdater-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/4491) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](http://www.apache.org/licenses/LICENSE-2.0)

## Contents
* [Description](https://github.com/hummatli/MAHAndroidUpdater#description)
* [Service structure](https://github.com/hummatli/MAHAndroidUpdater#service-structure)
* [Library structure](https://github.com/hummatli/MAHAndroidUpdater#library-structure)
* [Installation manual](https://github.com/hummatli/MAHAndroidUpdater#installation-manual)
* [Issues - Trouble with configuration?](https://github.com/hummatli/MAHAndroidUpdater#issues---trouble-with-configuration)
* [Releases - Upgrade documentation](https://github.com/hummatli/MAHAndroidUpdater#releases---upgrade-documentation)
* [To contribute](https://github.com/hummatli/MAHAndroidUpdater#to-contribute)
* [Contributors](https://github.com/hummatli/MAHAndroidUpdater#contributors)
* [Applications using MAHAndroidUpdater](https://github.com/hummatli/MAHAndroidUpdater#applications-using-mahandroidupdater)
* [Other libraries by developer](https://github.com/hummatli/MAHAndroidUpdater#other-libraries-by-developer)

## Description
MAHAndroidUpdater is library for notifing update information to installed android apps on android device. By it's help old application gets notified about update information from Google Play Market
Library has build on IDE `Android Studio` and binaries have added to `jcenter()`  `maven` repository.
<br>You can check  <a href="https://bintray.com/hummatli/maven/mah-android-updater#statistics">jCenter() download statistics</a> on this link - https://bintray.com/hummatli/maven/mah-android-updater#statistics  

There is a list of [application using MAHAndroidUpdater](https://github.com/hummatli/MAHAndroidUpdater#applications-using-mahandroidupdater). It would be nice if see your app link there too. If you use this library and want to see your app in the start of the [list](https://github.com/hummatli/MAHAndroidUpdater#applications-using-mahandroidupdater) please [inform me](mailto:settarxan@gmail.com) or send a pull request.

## Sample App in PlayStore
<a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahandroidupdater.sample">MAHAndroidUpdater - Sample</a> app has published on Google PlayStore. You can easly test module functionality with downloading it.
<br><a href="https://play.google.com/store/apps/details?id=com.mobapphome.mahandroidupdater.sample"><img src="https://raw.githubusercontent.com/hummatli/MAHAndroidUpdater/master/imgs/google-play-badge.png" width="200px"/></a> 

## Screenshots
<img src="https://raw.githubusercontent.com/hummatli/MAHAndroidUpdater/master/imgs/updater_dlg_small.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/MAHAndroidUpdater/master/imgs/restricter_dlg_small.png" width="200px"/>

## Service structure
To provide update information to your app you need to implement service responding json data about application current state. Structure of the json data is as below.  
You can provide `http://` and `https://` services. Library works both of them.

Json with sample data. [Link](https://github.com/MobAppHome/MAHServiceForMyApps/blob/master/public/mah_android_updater_dir/mah_android_updater_sample.json) to working sample
 
```json
{
    "is_run_mode":"true",
    "name":"MAHUpdater Sample",
    "uri_current":"com.mobapphome.mahandroidupdater.sample",
    "version_code_current":"2",
    "version_code_min":"1",
    "update_info":"On version 1.0 we added bla bla",
    "update_date":"16/07/2016"
}
```
* `is_run_mode` - service mode: if it's false modul will not react to service and will not show dialogs
* `name` - name of the belonging app
* `uri_current` - current package path
* `version_code_current` - current version code avilable
* `version_code_min` - Minimum version code, which does not work normal and had to force to update
* `update_info` - Update information
* `update_date` - Update date

If one of the variables would not be on json, then modul will not repond to service and act, Try to implement all data.  

You can check you json validity with this [jsonlint.com](http://jsonlint.com/)
  
## Library structure
Library contains from to Dialog component
* `MAHUpdaterDlg`- In this situation dialog show to user to update or install newer version and lets to postpone the action to later time and use application
* `MAHRestricterDlg` - In this situation dialog urges user to update or install newer version and dont alow use older version
 
The porpose of lib to show automatically these dialogs on application start if there are any need for it.
**-** `MAHUpdaterDlg` opens on following situation.
* Or `uri_current` value is different from app's installed package url
* Or `version_code_current` value is greater than app's installed version on device

**-** `MAHRestricterDlg` opens on all situation `MAHUpdaterDlg` opens and following situation.
* `version_code_min` value is greater than app's installed version on device

But when you develop your apps UI and want to show these dialogs there are test modes also and you can open dialogs by calling methods relatively 
* `MAHUpdaterController.testUpdaterDlg(activity);` - `MAHUpdaterDlg` 
* `MAHUpdaterController.testRestricterDlg(activity);` - `MAHRestricterDlg` 

## Installation manual
**1)** To import library to you project add following lines to project's `build.gradle` file.  
The last stable version is `1.1.0`

```
	dependencies {
    		compile 'com.mobapphome.library:mah-android-updater:1.1.0'
	}
```

**2)** On the start of your application call `MAHUpdaterController.init()` method to initialize modul. For example: MainActivity's `onCreate()` method or in splash activity. Check http url is correct and points to your service on the web.
Code: 
```java
	MAHUpdaterController.init(activity,"http://highsoft.az/mah-android-updater-sample.php");
```

**3)** When you quit app, you have to call `MAHUpdaterController.end()` method to finalize modul.  For example: MainActivity's `onDestroy()` method. 
```java
	MAHUpdaterController.end();						
```

**4)** To customize `MAHAndroidUpdater` dialog UI and overide colors set these values on your main projects `color.xml` file
```xml
    <color name="mah_android_upd_window_background_color">#FFFFFFFF</color>
    <color name="mah_android_upd_title_bar_color">#FF3F51B5</color>
    <color name="mah_android_upd_info_txt_color">#FF3F51B5</color>

    <color name="mah_android_upd_restricter_dlg_btn_pressed_color">#a33F51B5</color>
    <color name="mah_android_upd_restricter_dlg_btn_dark_state_color">#ff3F51B5</color>
    <color name="mah_android_upd_restricter_dlg_btn_light_state_color">#ffffffff</color>

    <color name="mah_android_upd_upd_dlg_btn_text_color">#ffFF4081</color>			
```

**5)** `Localization:`  Module now supports 4 languages ` (English, Azerbaijan, Portuguese, Russia, Turkey)` .  To set localization to app use your own method or if it is static and don't change in program session you can just simply add 		`LocaleUpdater.updateLocale(this, "your_lang");` in the start of your app. For examlpe  `LocaleUpdater.updateLocale(this, "ru");`

**6)** To customize `MAHAndroidUpdater` UI texts and overide them add these lines to main projects `string.xml` and set them values

```xml
    <string name="mah_android_upd_dlg_title">Update info</string>
    <!-- Button texts-->
    <string name="mah_android_upd_dlg_btn_no_later_txt">Later</string>
    <string name="mah_android_upd_dlg_btn_no_close_txt">Close</string>
    <string name="mah_android_upd_dlg_btn_yes_update_txt">Update</string>
    <string name="mah_android_upd_dlg_btn_yes_install_txt">Install</string>
    <string name="mah_android_upd_dlg_btn_yes_open_new_txt">Open new version</string>
    <string name="mah_android_upd_dlg_btn_no_uninstall_old_txt">Uninstall old</string>

    <!-- Info texts-->
    <string name="mah_android_upd_updater_info_install">Application has moved to new address. Please install newer version</string>
    <string name="mah_android_upd_updater_info_update">"New version is available. Please update application"</string>
    <string name="mah_android_upd_restricter_info_install">This is old version and does not operate. An application has moved to new address. \nPlease install newer version</string>
    <string name="mah_android_upd_restricter_info_update">"This is old version and does not operate. Please update application"</string>
    <string name="mah_android_upd_restricter_info_open_new_version">"This is old version and does not operate. Please open new version"</string>

    <!-- Additional information-->
    <string name="mah_android_upd_internet_update_error">Check your internet connection</string>
    <!--new--> <string name="mah_android_upd_play_service_not_found">Install Google Play Services to update application</string> 
    <string name="mah_android_upd_info_popup_text">MAHAndroidUpdater library</string>
```

**7)** To customize `Info button` on the `right - upper` corner of dialogs. You can do it with help of `MAHUpdaterController.init()` method. It has three version. `init()`This method well documented and you can see it when developing your app.  
You can do followings with `Info button`:
* Change visibility
* Set your own name or url to open when click on info button
* Open popup menu or act as button when click on info button


**8)** As modul takes information from web servcie you need add `INTERNET` permission to main project.
```xml
	<uses-permission android:name="android.permission.INTERNET" />
```

## Proguard configuration
MAHAndroidUpdater uses <a href="https://github.com/google/gson">Gson</a> and <a href="https://github.com/jhy/jsoup">Jsoup</a> libs. There for if you want to create your project with proguard you need to add following configuration to your proguard file.

```gradle
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }

##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Jsoup--------------------------------
-keep public class org.jsoup.** {
public *;
}
##---------------End: proguard configuration for Jsoup--------------------------------
```

## Issues - Trouble with configuration?
If you have any probelm with setting and using library please let me know. Create [issue](https://github.com/hummatli/MAHAndroidUpdater/issues) or write to settarxan@gmail.com. I will help.

## Releases - Upgrade documentation
See [releases](https://github.com/hummatli/MAHAndroidUpdater/releases). Please,read release notes to migrate your app from old version to a newer.

## To contribute
I am open to here offers and opinions from you 

* Fork it
* Create your feature branch (git checkout -b my-new-feature)
* Commit your changes (git commit -am 'Added some feature')
* Push to the branch (git push origin my-new-feature)
* Create new Pull Request
* Star it

### To contribute for localization
We need help to add new language localization support for libarary. If you have any hope to help us we were very happy and you can check following <i><a href="https://github.com/hummatli/MAHAndroidUpdater/issues">GitHub Issues URL</a></i> to contribute. To contribute get <a href="https://github.com/hummatli/MAHAndroidUpdater/blob/master/MAHAndroidUpdater/mah-android-updater/src/main/res/values/strings.xml">res/values/string.xml</a> file and translate to newer language. Place it on res/values-"spacific_lang"/string.xml

If you have any question please ask to me on <i><a href="mailto:settarxan@gmail.com">settarxan@gmail.com</a></i>

## Contributors
* Developer:
[Sattar Hummatli](https://github.com/hummatli) - [LinkedIn](https://www.linkedin.com/in/hummatli), settarxan@gmail.com, [Other libs](https://github.com/hummatli/MAHAndroidUpdater#other-libraries-by-developer)
* Translator `Portuguese`: [azzarr](https://github.com/azzarr)

## Applications using MAHAndroidUpdater
Please [ping](mailto:settarxan@gmail.com) me or send a pull request if you would like to see your app in the start of the list.

Icon | Application | Icon | Application
------------ | ------------- | ------------- | -------------
[Your app] |[ping](mailto:settarxan@gmail.com) me or send a pull request | <img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_en.png" width="48" height="48" /> | [Millionaire - in English](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.en)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_ru.png" width="48" height="48" /> | [Миллионер - на Pусском](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.ru) | <img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_tr.png" width="48" height="48" /> | [Milyoner - Türkçe](https://play.google.com/store/apps/details?id=com.mobapphome.millionaire.tr)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/millionaire_az.png" width="48" height="48" /> | [Milyonçu](https://play.google.com/store/apps/details?id=com.mobapphome.milyoncu) | <img src="https://lh3.ggpht.com/kfuLs-Ic0xR3SOFdjJ3FVeI0es2oXTCEt1T2y8tEVeYm7otSuSSBDlrpz4wXtIygf4k=w300-rw" width="48" height="48" /> | [Məzənnə](https://play.google.com/store/apps/details?id=com.mobapphome.currency)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/mah_ads_sample_icon.png" width="48" height="48" /> | [MAHAds - Sample](https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample) | <img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/mah_android_updater_sample_icon.png" width="48" height="48" /> | [MAHAndroidUpdater - Sample](https://play.google.com/store/apps/details?id=com.mobapphome.mahandroidupdater.sample)
<img src="https://project-943403214286171762.firebaseapp.com/mah_ads_dir/imgs/mah_encryptor_lib_sample_icon.png" width="48" height="48" /> | [MAHEncryptorLib - Sample](https://play.google.com/store/apps/details?id=com.mobapphome.mahencryptorlib) | <img src="https://lh5.ggpht.com/P_TyFmB5BzYDGWl3yliDHkQr_ttrYzHS3yQk3mBS3QuJJ5TJZ1pMj8lx-wmUmAHiUw=w300-rw" width="48" height="48" /> | [Ləzzət](https://play.google.com/store/apps/details?id=com.mobapphome.lezzet)

## Other libraries by developer
* [![MAHAds](https://img.shields.io/badge/GitHUB-MAHAds-green.svg)](https://github.com/hummatli/MAHAds) - Library for advertisement own apps through your other apps.  
* [![MAHEncryptorLib](https://img.shields.io/badge/GitHUB-MAHEncryptorLib-green.svg)](https://github.com/hummatli/MAHEncryptorLib) - Library for encryption and decryption strings on Android apps and PC Java applications.

## License
Copyright 2016  - <a href="https://www.linkedin.com/in/hummatli">Sattar Hummatli</a>   

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
