<h1 align="center">AndroidAppUpdater - Java (Kotlin, Android)</h1>
<h4 align="center">Android Library</h4>

<p align="center">
  <a target="_blank" href="https://bintray.com/hummatli/maven/android-app-updater/_latestVersion"><img src="https://api.bintray.com/packages/hummatli/maven/android-app-updater/images/download.svg"></a>
  <a target="_blank" href="https://android-arsenal.com/api?level=16"><img src="https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat"></a>
  <a target="_blank" href="http://www.apache.org/licenses/LICENSE-2.0"><img src="https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000"></a>
  <a target="_blank" href="https://android-arsenal.com/details/1/6993"><img src="https://img.shields.io/badge/Android%20Arsenal-AndroidAppUpdater-green.svg?style=flat"></a>
</p>

<p align="center">A free, open source, third party Android library to notify the update information about the installed android apps on an android device. The library has been built with the Kotlin language. Check out the <a href="https://github.com/hummatli/AndroidAppUpdater/wiki">wiki link</a>. To support, <a href="https://www.buymeacoffee.com/hummatli" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: 41px !important;width: 174px !important;box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;-webkit-box-shadow: 0px 3px 2px 0px rgba(190, 190, 190, 0.5) !important;" ></a></p> 

<!--[ ![Download](https://api.bintray.com/packages/hummatli/maven/android-app-updater/images/download.svg) ](https://bintray.com/hummatli/maven/android-app-updater/_latestVersion) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15) [![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?maxAge=2592000)](http://www.apache.org/licenses/LICENSE-2.0) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-_AndroidAppUpdater-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/4491)-->

<p align="center">
<img src="https://raw.githubusercontent.com/hummatli/AndroidAppUpdater/master/imgs/updater_dlg_small.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/AndroidAppUpdater/master/imgs/restricter_dlg_small.png" width="200px"/>
<img src="https://raw.githubusercontent.com/hummatli/AndroidAppUpdater/master/imgs/img3.png" width="200px"/>
</p>

## Description 
AndroidAppUpdater is a free, open source, third party Android library for notifing update information to android apps installed on an android device. By its help, an old application is notified about update information from the Google Play Market.
`Library has built with Kotlin language on Android Studio IDE` and binaries have added to `jcenter()`  `maven` repository.   
You can check  [jCenter() download statistics](https://bintray.com/hummatli/maven/android-app-updater#statistics) on this [link](https://bintray.com/hummatli/maven/android-app-updater#statistics)  
There is a list of [application using AndroidAppUpdater](https://github.com/hummatli/AndroidAppUpdater#applications-using-androidappupdater). It would be nice if see your app link there too. If you use this library and want to see your app in the start of the [list](https://github.com/hummatli/AndroidAppUpdater#applications-using-androidappupdater) please [inform me](mailto:settarxan@gmail.com) or send a pull request.

* [jCenter() download statistics](https://bintray.com/hummatli/maven/android-app-updater#statistics)
* [Application using AndroidAppUpdater](https://github.com/hummatli/AndroidAppUpdater#applications-using-androidappupdater)

<img src="https://raw.githubusercontent.com/hummatli/AndroidAppUpdater/master/imgs/green_star.png" width="20px"/>  _**Don't forget to start the protect to support us**_  

## Contributors
* Developer - Main functionality:
[Sattar Hummatli](https://github.com/hummatli) - [LinkedIn](https://www.linkedin.com/in/hummatli), settarxan@gmail.com, [Other libs](https://github.com/hummatli/AndroidAppUpdater#other-libraries-by-developer)
* Developer - Added new feature:
[andrewpros](https://github.com/andrewpros) , Thanks!, `New Feature: "Info Resolver"`. Now you can get information from your own formatted service
* Translator `Portuguese`: [azzarr](https://github.com/azzarr) , Thanks!
* Translator `Hindi`: [dalwadi2](https://github.com/dalwadi2), Thanks!
* Translator `German`: [Ndam Njoya](https://www.facebook.com/ndam.njoya), Thanks!
* Translator `French`: [Zeldarck](https://github.com/Zeldarck), Thanks!, Has changed sample apps to support `French`. 
* Translator `Greek`: [Nikos Linakis](https://github.com/hastoukopsaro), Thanks!.

## Contents
* [Description](https://github.com/hummatli/AndroidAppUpdater#description)
* [Service structure](https://github.com/hummatli/AndroidAppUpdater#service-structure)
* [Library structure](https://github.com/hummatli/AndroidAppUpdater#library-structure)
* [Installation manual](https://github.com/hummatli/AndroidAppUpdater#installation-manual)
* [Help - Issues](https://github.com/hummatli/AndroidAppUpdater#help---issues)
* [Releases - Upgrade documentation](https://github.com/hummatli/AndroidAppUpdater#releases---upgrade-documentation)
* [To contribute](https://github.com/hummatli/AndroidAppUpdater#to-contribute)
* [Contributors](https://github.com/hummatli/AndroidAppUpdater#contributors)
* [Localization](https://github.com/hummatli/AndroidAppUpdater#localization)
* [Applications using AndroidAppUpdater](https://github.com/hummatli/AndroidAppUpdater#applications-using-androidappupdater)
* [Other libraries by developer](https://github.com/hummatli/AndroidAppUpdater#other-libraries-by-developer)

## Demo App
<a href="https://github.com/hummatli/AndroidAppUpdater/releases/download/v1.1.7/DemoApp_AndroidAppUpdater.apk">AndroidAppUpdater - DemoApp</a> app has published on Google PlayStore. You can easly test module functionality with downloading it.

## Service structure
To provide update information to your app you need to implement service responding json data about application current state. Structure of the json data is as below.  
You can provide `http://` and `https://` services. Library works both of them.

Json with sample data. [Link](https://github.com/MobAppHome/MAHServiceForMyApps/blob/master/public/mah_android_updater_dir/mah_android_updater_sample.json) to working sample
 
```json
{
    "is_run_mode":"true",
    "name":"AndroidAppUpdater Sample",
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

There is interface called `IUpdateInfoResolver` by the help of it you can fetch your update information form own structed service. For example `JSON API`, `XML`,`Raw text` and so on. For this reason there is special `init()` method accepting this variable. This feature has added by @andrewpros.
  
## Library structure
`You can call with the same way in Kotlin and Java. Library contains samples both in Kotlin and Java`
Library components:
* `AAUpdaterDlg`- In this situation dialog show to user to update or install newer version and lets to postpone the action to later time and use application
* `AAUpdaterRestricterDlg` - In this situation dialog urges user to update or install newer version and dont alow use older version
 
The porpose of lib to show automatically these dialogs on application start if there are any need for it.
**-** `AAUpdaterDlg` opens on following situation.
* Or `uri_current` value is different from app's installed package url
* Or `version_code_current` value is greater than app's installed version on device

**-** `AAUpdaterRestricterDlg` opens on all situation `AAUpdaterDlg` opens and following situation.
* `version_code_min` value is greater than app's installed version on device

But when you develop your apps UI and want to show these dialogs there are test modes also and you can open dialogs by calling methods relatively 
* `AAUpdaterController.testUpdaterDlg(activity)` - `AAUpdaterDlg` 
* `AAUpdaterController.testRestricterDlg(activity)` - `AAUpdaterRestricterDlg` 

## Installation manual
The lib has compiled with the gradle 4.6.

**1)** To import library to you project add following lines to project's `build.gradle` file.  
The last stable version is `1.3.3`

```gradle
repositories {
    maven { url 'https://dl.bintray.com/hummatli/maven/' }
}

dependencies {
     //The android-app-updater libary
    implementation 'com.mobapphome.library:android-app-updater:1.3.3'
    
    //These dependencies have been used in this lib so you need to include them acording to gradle 3.0 and upper
    implementation 'com.google.code.gson:gson:2.8.2'
    implementation 'org.jsoup:jsoup:1.10.2'
}
```

**2)** On the start of your application call `AAUpdaterController.init()` method to initialize modul. For example: MainActivity's `onCreate()` method or in splash activity. Check http url is correct and points to your service on the web.
Code: 
```java kotlin
    AAUpdaterController.init(activity,"http://highsoft.az/android-app-updater-sample.php")
```

**3)** When you quit app, you have to call `AAUpdaterController.end()` method to finalize modul.  For example: MainActivity's `onDestroy()` method. 
```java kotlin
    AAUpdaterController.end()					
```

**4)** To customize `AndroidAppUpdater` dialog UI and overide colors set these values on your main projects `color.xml` file
```xml
    <color name="android_app_upd_window_background_color">#FFFFFFFF</color>
    <color name="android_app_upd_title_bar_color">#FF3F51B5</color>
    <color name="android_app_upd_info_txt_color">#FF3F51B5</color>

    <color name="android_app_upd_restricter_dlg_btn_pressed_color">#a33F51B5</color>
    <color name="android_app_upd_restricter_dlg_btn_dark_state_color">#ff3F51B5</color>
    <color name="android_app_upd_restricter_dlg_btn_light_state_color">#ffffffff</color>

    <color name="android_app_upd_upd_dlg_btn_text_color">#ffFF4081</color>			
```

**5)** `Localization:`  Following languages is supporting by the lib - [Supported Languages](https://github.com/hummatli/AndroidAppUpdater#localization).  To set localization to app use your own method or if it is static and don't change in program session you can just simply add `LocaleUpdater.updateLocale(this, "your_lang");` in the start of your app. For examlpe  `LocaleUpdater.updateLocale(this, "ru");`

**6)** To customize `AndroidAppUpdater` UI texts and overide them add these lines to main projects `string.xml` and set them values.  
To help translators there prefixes on the name of strings
* < command verb (actions)> - These are commands verbs. Meaninaction on UI , dialogs
* < noun > - these are nouns not action (verb)

```xml
    <!-- noun --> <string name="noun_android_app_upd_dlg_title">Update information</string>
    <!-- Button texts-->
    <string name="android_app_upd_dlg_btn_no_later_txt">Later</string>
    <!--command verb--> <string name="cmnd_verb_android_app_upd_dlg_btn_no_close_txt">Close</string>
    <!--command verb--> <string name="cmnd_verb_android_app_upd_dlg_btn_yes_update_txt">Update</string>
    <!--command verb--> <string name="cmnd_verb_android_app_upd_dlg_btn_yes_install_txt">Install</string>
    <string name="android_app_upd_dlg_btn_yes_open_new_txt">Open new version</string>
    <!--command verb--> <string name="cmnd_verb_android_app_upd_dlg_btn_no_uninstall_old_txt">Uninstall old</string>

    <!-- Info texts-->
    <string name="android_app_upd_updater_info_install">Application has moved to new address. Please install newer version</string>
    <string name="android_app_upd_updater_info_update">New version is available. Please update application</string>
    <string name="android_app_upd_restricter_info_install">This is old version and does not operate. An application has moved to new address. \nPlease install newer version</string>
    <string name="android_app_upd_restricter_info_update">This is old version and does not operate. Please update application</string>
    <string name="android_app_upd_restricter_info_open_new_version">This is old version and does not operate. Please open new version</string>

    <!-- Additional information-->
    <string name="android_app_upd_internet_update_error">Check your internet connection</string>
    <string name="android_app_upd_play_service_not_found">Install Google Play Services to update application</string>
    <string name="android_app_upd_info_popup_text">\"AndroidAppUpdater\" library</string>
```

**7)** To customize `Info button` on the `right - upper` corner of dialogs. You can do it with help of `AAUpdaterController.init()` method. It has three version. `init()`This method well documented and you can see it when developing your app.  
You can do followings with `Info button`:
* Change visibility
* Set your own name or url to open when click on info button
* Open popup menu or act as button when click on info button


**8)** As modul takes information from web servcie you need add `INTERNET` permission to main project.
```xml
    <uses-permission android:name="android.permission.INTERNET" />
```

## Proguard configuration
AndroidAppUpdater uses <a href="https://github.com/google/gson">Gson</a> and <a href="https://github.com/jhy/jsoup">Jsoup</a> libs. There for if you want to create your project with proguard you need to add proguard configuration to your proguard file. Look at [Progurda File](https://github.com/hummatli/AndroidAppUpdater/blob/master/proguard-rules-android-app-updater.pro).

## Help - Issues
If you have any problem with setting and using library or want to ask question, please let me know. Create [issue](https://github.com/hummatli/AndroidAppUpdater/issues) or write to <i><a href="mailto:settarxan@gmail.com">settarxan@gmail.com</a></i>. I will help.

<!--## Releases - Upgrade documentation
See [releases](https://github.com/hummatli/AndroidAppUpdater/releases). Please,read release notes to migrate your app from old version to a newer.-->

## To contribute
I am open to hear offers and opinions from you 

* Fork it
* Create your feature branch (git checkout -b my-new-feature)
* Commit your changes (git commit -am 'Added some feature')
* Push to the branch (git push origin my-new-feature)
* Create new Pull Request
* Star it

## Localization
Library now supports following languages 
* Azerbaijan
* English
* German
* Greek
* Hindi
* Portuguese
* Russia
* Turkey
* [Add your language](https://github.com/hummatli/AndroidAppUpdater/blob/master/README.md#to-contribute-for-localization)

### To contribute for localization
**To help translator in context I have added prefixes to the start of the string names.
Be carefull when translating. Prefixes are following:**   
_* < command verb (actions)> - These are commands verbs. Meaninaction on UI , dialogs_   
_* < noun > - these are nouns not action (verb)_    

We need help to add new language localization support for libarary. If you have any hope to help us we were very happy and you can check following <i><a href="https://github.com/hummatli/AndroidAppUpdater/issues">GitHub Issues URL</a></i> to contribute. To contribute get <a href="https://github.com/hummatli/AndroidAppUpdater/blob/master/android-app-updater/src/main/res/values/strings.xml</a> file and translate to newer language. Place it on res/values-"spacific_lang"/string.xml   

## Applications using AndroidAppUpdater
Please [ping](mailto:settarxan@gmail.com) me or send a pull request if you would like to see your app in the start of the list.

Icon | Application | Icon | Application
------------ | ------------- | ------------- | -------------
[Your app] |[ping](mailto:settarxan@gmail.com) me or send a pull request | <img src="https://lh3.googleusercontent.com/UhNXotmmhpK3eCV5XEeLSX555Tu_k-A9VgqrPK_4EWLJsJaCUugNVGEahafCrO45Lg=w300-rw" width="48" height="48" /> | [Indian Railway PNRStatus IRCTC](https://play.google.com/store/apps/details?id=com.emilartin.travel.indianrailwaypnrstatusirctc)
<img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_ru.png" width="48" height="48" /> | [Миллионер - на Pусском](https://play.google.com/store/apps/details?id=iqra.viktorina.intellektualnoy.iq.millionaire.russian.millioner.russkiy) | <img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_tr.png" width="48" height="48" /> | [Milyoner - Türkçe](https://play.google.com/store/apps/details?id=oyun.bilgi.entellektuel.iq.millionaire.turkish.milyoner.turkce)
<img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_en.png" width="48" height="48" /> | [Millionaire - in English](https://play.google.com/store/apps/details?id=game.quiz.intellectual.iq.millionaire.english) | <img src="https://lh3.ggpht.com/kfuLs-Ic0xR3SOFdjJ3FVeI0es2oXTCEt1T2y8tEVeYm7otSuSSBDlrpz4wXtIygf4k=w300-rw" width="48" height="48" /> | [Məzənnə](https://play.google.com/store/apps/details?id=com.mobapphome.currency)
<img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/mah_ads_sample_icon.png" width="48" height="48" /> | [AppCrossPromoter - Sample](https://play.google.com/store/apps/details?id=com.mobapphome.mahads.sample) | <img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/mah_android_updater_sample_icon.png" width="48" height="48" /> | [AndroidAppUpdater - Sample](https://play.google.com/store/apps/details?id=androidappupdater.sample)
<img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/mah_encryptor_lib_sample_icon.png" width="48" height="48" /> | [SimpleEncryptorLib - Sample](https://play.google.com/store/apps/details?id=com.mobapphome.mahencryptorlib) | <img src="https://lh5.ggpht.com/P_TyFmB5BzYDGWl3yliDHkQr_ttrYzHS3yQk3mBS3QuJJ5TJZ1pMj8lx-wmUmAHiUw=w300-rw" width="48" height="48" /> | [Ləzzət](https://play.google.com/store/apps/details?id=com.mobapphome.lezzet)
<img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_az.png" width="48" height="48" /> | [Milyonçu](https://play.google.com/store/apps/details?id=oyun.test.sualcavab.iq.millionaire.azerbaijani.milyoncu.azerbaycanca) | <img src="https://project-943403214286171762.firebaseapp.com/imgs_for_github_readmes/millionaire_de.png" width="48" height="48" />| [Millionär - Deutsche](https://play.google.com/store/apps/details?id=spiel.quiz.intellektuell.iq.millionaire.german.millionar.deutsche)


## Other libraries by developer
* [![AppCrossPromoter](https://img.shields.io/badge/GitHUB-AppCrossPromoter-green.svg)](https://github.com/hummatli/AppCrossPromoter) - Library for advertisement own apps through your other apps.  
* [![SimpleEncryptorLib](https://img.shields.io/badge/GitHUB-SimpleEncryptorLib-green.svg)](https://github.com/hummatli/SimpleEncryptorLib) - Library for encryption and decryption strings on Android apps and PC Java applications.

## License
Copyright 2017  - <a href="https://www.linkedin.com/in/hummatli">Sattar Hummatli</a>   

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
