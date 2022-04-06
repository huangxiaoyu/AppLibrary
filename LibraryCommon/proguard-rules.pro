## Add project specific ProGuard rules here.
## You can control the set of applied configuration files using the
## proguardFiles setting in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#
## Uncomment this to preserve the line number information for
## debugging stack traces.
##-keepattributes SourceFile,LineNumberTable
#
## If you keep the line number information, uncomment this to
## hide the original source file name.
##-renamesourcefileattribute SourceFile
#
#

#----------友盟--------------------
-keep class com.umeng.** {*;}

-keep class com.uc.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.zui.** {*;}
-keep class com.miui.** {*;}
-keep class com.heytap.** {*;}
-keep class a.** {*;}
-keep class com.vivo.** {*;}
#SDK需要引用导入工程的资源文件，通过了反射机制得到资源引用文件R.java，但是在开发者通过proguard等混淆/优化工具处理apk时，proguard可能会将R.java删除，如果遇到这个问题，请添加如下配置
-keep public class [您的应用包名].R$*{
public static final int *;
}
#----------友盟 end--------------------

#----------sharesdk start--------------------
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class com.mob.**{*;}
-keep class com.bytedance.**{*;}
-dontwarn cn.sharesdk.**
-dontwarn com.sina.**
-dontwarn com.mob.**
#----------sharesdk end--------------------



#----------SweetAlert end--------------
-keep class cn.pedant.SweetAlert.Rotate3dAnimation {
  public <init>(...);
}
#----------SweetAlert end--------------

#----------glide end--------------
#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
#----------glide end--------------

#----------FastJson end--------------
# FastJson
-dontwarn com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
#----------FastJson end--------------

# OkHttp3
-dontwarn okhttp3.logging.**
-keep class okhttp3.internal.**{*;}
-dontwarn okio.**
# OkHttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
# Retrofit
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn sun.misc.**
-dontwarn sorg.codehaus.mojo.animal_sniffer.**
-dontwarn org.codehaus.**
-dontwarn java.nio.**
-dontwarn java.lang.invoke.**
# RxJava RxAndroid
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}

################gson##################
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }


#----------PictureSelector start--------------
#PictureSelector 2.0
-keep class com.luck.picture.lib.** { *; }

#Ucrop
-dontwarn com.yalantis.ucrop**
-keep class com.yalantis.ucrop** { *; }
-keep interface com.yalantis.ucrop** { *; }

#Okio
-dontwarn org.codehaus.mojo.animal_sniffer.*
#----------PictureSelector end--------------