object Plugins {
    object Gradle {
        const val ANDROID = "com.android.tools.build:gradle:7.1.3"
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10"
        const val HILT = "com.google.dagger:hilt-android-gradle-plugin:2.43.2"
    }
    const val SERIALIZATION = "plugin.serialization"
    const val HILT = "dagger.hilt.android.plugin"
    const val JAVA = "java-library"
    const val JVM = "org.jetbrains.kotlin.jvm"
    const val safeArgs = "androidx.navigation.safeargs.kotlin"
    object AGP {
        const val APPLICATION = "com.android.application"
        const val LIBRARY = "com.android.library"
    }
    object Kotlin {
        const val ANDROID = "android"
        const val KAPT = "kapt"
    }
}