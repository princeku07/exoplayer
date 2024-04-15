plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.xperiencelabs.exoplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.xperiencelabs.exoplayer"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    implementation (libs.media3.exoplayer)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}