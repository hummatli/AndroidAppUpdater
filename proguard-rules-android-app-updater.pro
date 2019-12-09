##-----------------To show exceptions right --------------------------------------
-keep public class * extends java.lang.Exception


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

-keepclassmembers enum * { *; }

##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Jsoup--------------------------------
-keep public class org.jsoup.** {
public *;
}
##---------------End: proguard configuration for Jsoup--------------------------------
