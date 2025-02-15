

# Add project specific ProGuard rules here.

# Keep Retrofit
-keepattributes Signature
-keepattributes *Annotation*
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# Keep Gson
-keep class com.google.gson.** { *; }
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Keep your data models
-keep class com.biprangshu.pokedex.data.** { *; }
-keep class com.biprangshu.pokedex.domain.** { *; }

# Keep Hilt generated components and modules
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel
-keep class * extends dagger.hilt.components.** { *; }
-keep class * extends dagger.hilt.android.components.** { *; }
-keep class * extends dagger.hilt.internal.** { *; }

# Keep Retrofit API interfaces
-keep interface com.biprangshu.pokedex.data.api.** { *; }

# Keep ViewModels
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Keep Parcelable classes
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Uncomment this to preserve the line number information for debugging stack traces.
# -keepattributes SourceFile,LineNumberTable

# Keep all annotations (if needed)
-keepattributes *Annotation*
