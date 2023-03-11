buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Plugins.Gradle.ANDROID)
        classpath(Plugins.Gradle.KOTLIN)
        classpath(Plugins.Gradle.HILT)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
