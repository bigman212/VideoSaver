import java.io.File
import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.application")
//    id "com.google.gms.google-services"
    kotlin("android")
    id("kotlin-parcelize")
    kotlin("kapt")
}


val localProperties = Properties().apply {
    load(File(rootProject.rootDir,"local.properties").inputStream())
}

val kotlinVersion: String by rootProject.extra

android {
    namespace = "com.bill.videosaver"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.bill.videosaver"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        val baseUrl: String by localProperties

        buildConfigField("String", "BASE_URL", baseUrl)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += "room.schemaLocation" to "$projectDir/schemas".toString()
            }
        }

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {

        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName = "videosaver-${variant.baseName}-${variant.versionName}-${variant.versionCode}.apk"
                output.outputFileName = outputFileName
            }
    }

//    testOptions {
//        unitTests.all { useJUnitPlatform() }
//        unitTests.returnDefaultValues = true
//        unitTests.includeAndroidResources = true
//        animationsDisabled = true
//        execution "ANDROIDX_TEST_ORCHESTRATOR"
//    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions.jvmTarget = "1.8"
    buildFeatures.viewBinding = true
}

dependencies {
    implementation(project(":shared"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    implementation("androidx.appcompat:appcompat:1.6.0")

    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-ktx:1.6.1")
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    val lifecycle_version = "2.5.1"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("com.google.android.material:material") {
        version { strictly("1.7.0") }
    }

    implementation("com.airbnb.android:epoxy:5.1.1")

    implementation("com.github.terrakok:cicerone:7.1")
    implementation("com.redmadrobot.extensions:viewbinding-ktx:4.2.1-0")

    // Koin Test features
    val koinVersion = "3.2.0"
    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")

    // Room
    val roomVersion = "2.5.0"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt ("androidx.room:room-compiler:$roomVersion")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt ("com.github.bumptech.glide:compiler:4.12.0")

    implementation("com.google.android.gms:play-services-base:18.1.0")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")

    val okHttpVersion = "4.9.3"
    implementation("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("com.squareup.okhttp3:logging-interceptor:$okHttpVersion")

    implementation("com.jakewharton.timber:timber:5.0.1")

    implementation("io.insert-koin:koin-android:$koinVersion")
    implementation("io.insert-koin:koin-androidx-workmanager:$koinVersion")

    val arrowVersion = "1.0.1"
    implementation("io.arrow-kt:arrow-core:$arrowVersion")
    implementation("io.arrow-kt:arrow-fx-coroutines:$arrowVersion")

    val coroutinesVersion = "1.6.4"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    testImplementation("org.assertj:assertj-core:3.22.0")

    testImplementation("junit:junit:4.13.2")
}