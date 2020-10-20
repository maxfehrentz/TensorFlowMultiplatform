plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}
group = "com.example.tensorflowmultiplatform"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}

apply(file("download.gradle"))

dependencies {
    implementation(project(":shared"))
    implementation("com.google.android.material:material:1.2.0")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("org.tensorflow:tensorflow-lite:2.2.0")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.2.0")
}
android {
    compileSdkVersion(29)
    defaultConfig {
        applicationId = "com.example.tensorflowmultiplatform.androidApp"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    aaptOptions {
        noCompress("tflite")
    }
}