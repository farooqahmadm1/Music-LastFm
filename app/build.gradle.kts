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

    /**
     * Hilt
     */
    implementation(Hilt.android)
    kapt(Hilt.compiler)
    androidTestImplementation(HiltTest.hiltAndroidTesting)
    kaptAndroidTest(HiltTest.hiltKaptTesting)


    implementation("androidx.test:core:1.4.0")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("junit:junit:4.12")

    testImplementation("androidx.test.ext:truth:1.4.0")
    androidTestImplementation("androidx.test.ext:truth:1.4.0")

    testImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    testImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:3.4.0")

    testImplementation("com.google.truth:truth:1.0.1")
    androidTestImplementation("com.google.truth:truth:1.0.1")

    testImplementation(Kotlinx.coroutinesTest)
    androidTestImplementation(Kotlinx.coroutinesTest)

    implementation("androidx.test.ext:junit-ktx:1.1.3")

    implementation("androidx.legacy:legacy-support-v4:1.0.0")
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


    val room_version = "2.4.3"

    implementation("androidx.room:room-runtime:$room_version")
//    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")
    testImplementation("androidx.room:room-testing:$room_version")
    implementation("androidx.room:room-paging:$room_version")

    val paging_version = "3.1.1"
    implementation("androidx.paging:paging-runtime:$paging_version")
    testImplementation("androidx.paging:paging-common:$paging_version")
}


// Allow references to generated code
kapt {
    correctErrorTypes = true
}