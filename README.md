# Cubits for Android
This framework brings the Cuibt architecture to Android.
Cubit is a light weight [BLoC](https://github.com/felangel/bloc/tree/master/packages/bloc) without the concept of events.

**NOTE**: This framework in alpha. So please feel free to test it, create issues or to give feedback.

# Gradle
1. Add in your project build.gradle
```groovy
allprojects {
   repositories {
      ...
      maven { url 'https://jitpack.io' }
   }
}
```

2. Add in you module build.gradle
```groovy
dependencies {
   implementation 'com.github.Usorsoft:android-cubit-architecture:0.1.0'
}
```
