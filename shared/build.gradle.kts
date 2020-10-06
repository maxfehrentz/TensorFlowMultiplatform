import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-android-extensions")
    kotlin("native.cocoapods")
}
group = "com.example.tensorflowmultiplatform"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
}
kotlin {
    android()
    ios()
    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Testing TensorFlow"
        homepage = "No link provided."
        // You can change the name of the produced framework.
        // By default, it is the name of the Gradle project.
        frameworkName = "shared"
        ios.deploymentTarget = "13.0"
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.koin:koin-core:3.0.1-alpha-2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.0")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.12")
            }
        }
        // distinction between x64 and arm64 necessary?
        val iosX64Main by getting {
            kotlin.srcDir("iosMain")
        }
        val iosArm64Main by getting {
            kotlin.srcDir("iosMain")
        }
        val iosTest by getting
    }
}
android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
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
}