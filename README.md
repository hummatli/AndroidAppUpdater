# MAHAndroidUpdater - <a href="https://play.google.com/store/apps/developer?id=MobAppHome">MobAppHome</a>  android update helper library

MAHAndroidUpdater is library for notifing update information to installed android apps on android device. By it's help old application gets notified about update information from Google Play Market
Library has build on IDE `Android Studio` and binaries have added to `jcenter()`  `maven` repository.

#Images
<img src="https://github.com/hummatli/MAHAndroidUpdater/blob/master/imgs/updater_dlg.png" width="200px"/>
<img src="https://github.com/hummatli/MAHAndroidUpdater/blob/master/imgs/restricter_dlg.png" width="200px"/>

#Service structure
To provide update information to your app you need to implement service responding json data about application current state. Structure of the json data is as below.

Json with sample data 
 
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
  
#Library structure
Library contains from to Dialog component
* `MAHUpdaterDlg`- In this situation dialog show to user to update or install newer version and lets to postpone the action to later time and use application
* `MAHRestricterDlg` - In this situation dialog urges user to update or install newer version and dont alow use older version
 
The porpose of lib to show automatically these dialogs on application start if there are any need for it.
<b>-</b> `MAHUpdaterDlg` opens on following situation.
* Or `uri_current` value is different from app's installed package url
* Or `version_code_current` value is greater than app's installed version on device

<b>-</b> `MAHRestricterDlg` opens on all situation `MAHUpdaterDlg` opens and following situation.
* `version_code_min` value is greater than app's installed version on device

But when you develop your apps UI and want to show these dialogs there are test modes also and you can open dialogs by calling methods relatively 
* `MAHUpdaterController.testUpdaterDlg(activity);` - `MAHUpdaterDlg` 
* `MAHUpdaterController.testRestricterDlg(activity);` - `MAHRestricterDlg` 

#Installation manual

<b>`1)`</b> To import library to you project add following lines to project's `build.gradle` file. The last stable version is `1.0.9`

```
	dependencies {
    		compile 'com.mobapphome.library:mah-android-updater:1.0.9'
	}
```

<b>`2)`</b> On the start of your application call `MAHUpdaterController.init()` method to initialize modul. For example: MainActivity's `onCreate()` method or in splash activity. Check http url is correct and points to your service on the web.
Code: 
```java
	MAHUpdaterController.init(activity,"http://highsoft.az/mah-android-updater-sample.php");
```


<b>`3)`</b> When you quit app, you have to call `MAHUpdaterController.end()` method to finalize modul.  For example: MainActivity's `onDestroy()` method. 
```java
	MAHUpdaterController.end();						
```

<b>`4)`</b> To customize `MAHAndroidUpdater` dialog UI and overide colors set these values on your main projects `color.xml` file
```xml
    <color name="mah_android_upd_window_background_color">#FFFFFFFF</color>
    <color name="mah_android_upd_title_bar_color">#FF3F51B5</color>
    <color name="mah_android_upd_info_txt_color">#FF3F51B5</color>

    <color name="mah_android_upd_restricter_dlg_btn_pressed_color">#a33F51B5</color>
    <color name="mah_android_upd_restricter_dlg_btn_dark_state_color">#ff3F51B5</color>
    <color name="mah_android_upd_restricter_dlg_btn_light_state_color">#ffffffff</color>

    <color name="mah_android_upd_upd_dlg_btn_text_color">#ffFF4081</color>			
```

<b>`5)`</b> To customize `MAHAndroidUpdater` UI texts and overide them add these lines to main projects `string.xml` and set them values

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
    <string name="mah_android_upd_info_popup_text">MAHAndroidUpdater library</string>
```
    	
<b>`6)`</b> As modul takes information from web servcie you need add `INTERNET` permission to main project.
```xml
	<uses-permission android:name="android.permission.INTERNET" />
```

#End
Thats all. If you have any probelm with setting and using library please let me know. Write to settarxan@gmail.com. I will help.

#Proguard configuration
Library uses Gson and Jsoup libs. There for if you want to create your project with proguard you need to add proguard file to you proguard fli list.
File has located in MAHAndroidUpdater/proguard-rules-mah-android-updater.pro

#Contribution
I am open to here offers and opinions from you 

* Fork it
* Create your feature branch (git checkout -b my-new-feature)
* Commit your changes (git commit -am 'Added some feature')
* Push to the branch (git push origin my-new-feature)
* Create new Pull Request
* Star it

#Developed By
Sattar Hummatli - settarxan@gmail.com


#License
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
