buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("com.android.tools.build:gradle:4.0.1")
//        classpath("co.touchlab:kotlinnativecocoapods:0.11")
    }
    group = "com.example.tensorflowmultiplatform"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
//        maven(url = "https://dl.bintray.com/ekito/koin")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        maven(url = "https://dl.bintray.com/touchlabpublic/kotlin")
    }
}
