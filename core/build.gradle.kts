plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.farooq.core"
    compileSdk = 33

    defaultConfig {
        minSdk = 21
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField(Config.STRING, Config.BASE_URL, Config.Application.BASE_URL)
            buildConfigField(Config.STRING, Config.Keys.API_KEY_NAME, Config.Keys.API_KEY_VALUE)
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {
            buildConfigField(Config.STRING, Config.BASE_URL, Config.Application.BASE_URL)
            buildConfigField(Config.STRING, Config.Keys.API_KEY_NAME, Config.Keys.API_KEY_VALUE)
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
     * Logger
     */
    implementation(Depends.timber)

    /**
     * Hilt
     */
    implementation(Hilt.android)
    kapt(Hilt.compiler)
    kapt(HiltTest.hiltAndroidTesting)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.google.android.material:material:1.7.0")

    /**
     * retrofit + okhttp3
     */
    implementation(Retrofit.retrofit)
    implementation(Retrofit.gsonConverter)
    implementation(platform(OkHttp3.BOM))
    implementation(OkHttp3.okhttp)
    implementation(OkHttp3.logger)


    /**
     * Navigation
     */
    implementation(Navigation.navigationFragment)
    implementation(Navigation.navigationUi)

    val paging_version = "3.1.1"
    implementation("androidx.paging:paging-runtime:$paging_version")
    testImplementation("androidx.paging:paging-common:$paging_version")

}