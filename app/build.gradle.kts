plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.reallogin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.reallogin"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation ("com.google.android.gms:play-services-location:21.0.1")
        implementation ("com.squareup.okhttp3:okhttp:4.10.0")
        implementation ("org.json:json:20210307")
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.27")
    implementation ("com.otaliastudios:zoomlayout:1.9.0")
    implementation ("com.github.chrisbanes:PhotoView:2.3.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
    implementation("org.jitsi.react:jitsi-meet-sdk:10.1.2") {
        isTransitive = true
    }
        implementation(libs.firebase.auth.v2230)
        implementation(libs.androidx.appcompat.v161)
        implementation(libs.material.v190)
        implementation(libs.androidx.constraintlayout.v214)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}