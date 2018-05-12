# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-optimizationpasses 5				# 指定代码的压缩级别
-dontusemixedcaseclassnames			# 是否使用大小写混合
-dontskipnonpubliclibraryclasses	# 是否混淆第三方jar
-dontpreverify						# 混淆时是否做预校验
-verbose							# 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*	# 混淆时所采用的算法

#忽略警告 也可以用-ignorewarnings
-ignorewarnings

-keepattributes *Annotation*		#注解
-keepattributes Signature

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.**
-keep public class com.android.vending.licensing.ILicensingService

-keep class android.support.v4.** { *; }
#-keep class android.** { *; }
-keep class android.content.** { *; }
-keep class com.android.** { *; }
-keep class com.google.** { *; }
-keep class android_serialport_api.** { *; }
-keep class com.studio.cloudelevator.entity.** { *; }
#-keep class com.studio.cloudelevator.task.** { *; }

-dontwarn android.support.v4.**
-dontwarn android.studio.**
-dontwarn com.android.**
-dontwarn com.google.**

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   	public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
