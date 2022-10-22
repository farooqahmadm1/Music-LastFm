// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    val compose_version by extra("1.2.1")
    val gradle_version by extra("7.3.1")
    val kotlin_plugin_version by extra("1.7.10")
    val kotlin_core_version by extra("1.8.0")
    val navigationComponent_version by extra("2.5.0")
    val hilt_version by extra("2.43.2")

    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${rootProject.extra["gradle_version"]}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${rootProject.extra["kotlin_plugin_version"]}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${rootProject.extra["navigationComponent_version"]}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${rootProject.extra["hilt_version"]}")
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}




