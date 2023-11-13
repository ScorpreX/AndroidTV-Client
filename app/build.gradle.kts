plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.unideb.fvass.letswatchit"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.unideb.fvass.letswatchit"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.leanback:leanback:1.0.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.media3:media3-exoplayer:1.1.1")
    implementation("androidx.media3:media3-ui-leanback:1.1.1")

    val lifecycleVersion = "2.6.2"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    /*viewmodel*/
    implementation("androidx.fragment:fragment-ktx:1.6.1")

    /*coil*/
    implementation("io.coil-kt:coil:2.4.0")

    /*exoplayer*/
    implementation("androidx.media3:media3-exoplayer:1.1.1")
    implementation("androidx.media3:media3-ui:1.1.1")
    implementation("androidx.media3:media3-exoplayer:1.1.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.1.1")
    implementation("androidx.media3:media3-ui:1.1.1")
    implementation("androidx.media3:media3-exoplayer-hls:1.1.1")
    implementation("com.github.bjoernpetersen:m3u-parser:1.3.0")
    implementation("androidx.media3:media3-datasource-okhttp:1.1.1")
    implementation("androidx.media3:media3-decoder:1.1.1")

//    implementation("androidx.media3:media3-ui-leanback:1.1.1")
//    implementation("androidx.media3:media3-exoplayer-ima:1.1.1")
//    implementation("androidx.media3:media3-datasource-rtmp:1.1.1")
//    implementation("androidx.media3:media3-muxer:1.1.1")
//    implementation("androidx.media3:media3-exoplayer-smoothstreaming:1.1.1")
//    implementation("androidx.media3:media3-effect:1.1.1")
//    implementation("androidx.media3:media3-session:1.1.1")
//    implementation("androidx.media3:media3-datasource-cronet:1.1.1")
//    implementation("androidx.media3:media3-exoplayer-workmanager:1.1.1")
//    implementation("androidx.media3:media3-test-utils:1.1.1")
//    implementation("androidx.media3:media3-cast:1.1.1")
//    implementation("androidx.media3:media3-transformer:1.1.1")
//    implementation("androidx.media3:media3-container:1.1.1")
//    implementation("androidx.media3:media3-common:1.1.1")
//    implementation("androidx.media3:media3-ui:1.1.1")
//    implementation("androidx.media3:media3-datasource:1.1.1")
//    implementation("androidx.media3:media3-database:1.1.1")
//    implementation("androidx.media3:media3-extractor:1.1.1")
//    implementation("androidx.media3:media3-exoplayer-rtsp:1.1.1")
//    implementation("com.google.android.exoplayer:extension-ffmpeg:2.14.1")
















}