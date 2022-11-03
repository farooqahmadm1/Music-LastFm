plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs")
}

android {

    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Config.Application.applicationId
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = Android.versionCode
        versionName = Android.versionName
        multiDexEnabled = true
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        testInstrumentationRunner = "com.farooq.lastfm.MyTestRunner"

        buildTypes {
            getByName(Config.Variants.debug) {
                isShrinkResources = false
                isMinifyEnabled = false
            }
            getByName(Config.Variants.release) {
                isShrinkResources = true
                isMinifyEnabled = true
            }
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(Modules.core))

    /**
     * Android X and Basic Libraries
     */
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.activity)
    implementation(AndroidX.fragment)
    implementation(AndroidX.recyclerView)
    implementation(AndroidX.legacySupport)
    implementation(AndroidX.constraintLayout)
    implementation(AndroidX.browser)
    implementation(AndroidX.lifecycleVmKtx)
    implementation(AndroidX.lifecycleLdKtx)

    /**
     * GOOGLE
     */
    implementation(Google.material)
    implementation(Google.gson)
    testImplementation(Google.truth)
    androidTestImplementation(Google.truth)


    /**
     * Navigation
     */
    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)


    /**
     * Kotlin
     */
    implementation(Kotlin.kotlinStdlib)
    implementation(Kotlinx.datetime)
    implementation(Kotlinx.coroutinesCore)
    implementation(Kotlinx.coroutinesAndroid)
    testImplementation(Kotlinx.coroutinesTest)
    androidTestImplementation(Kotlinx.coroutinesTest)

    /**
     * Hilt
     */
    implementation(Hilt.android)
    kapt(Hilt.compiler)
    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(HiltTest.hiltKaptTesting)

    implementation("androidx.test:core:1.4.0")
    implementation("androidx.test.ext:junit-ktx:1.1.3")

    testImplementation(Junit.junit4)
    testImplementation("androidx.test.ext:truth:1.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("androidx.test:rules:1.4.0")

    androidTestImplementation(Junit.junit4)
    androidTestImplementation("androidx.test.ext:truth:1.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:3.4.0")


    /**
     * retrofit + okhttp3
     */
    implementation(Retrofit.retrofit)
    implementation(Retrofit.gsonConverter)
    implementation(platform(OkHttp3.BOM))
    implementation(OkHttp3.okhttp)
    implementation(OkHttp3.logger)

    /**
     * Logger
     */
    implementation(Depends.timber)

    /**
     * Coil to load images
     */
    implementation(Coil.coil)

    implementation(Room.kotlin)
    implementation(Room.runtime)
    implementation(Room.paging)
    kapt(Room.compiler)
    testImplementation(Room.test)

    implementation(Paging.paging)
    testImplementation(Paging.pagingTest)
}


// Allow references to generated code
kapt {
    correctErrorTypes = true
}