buildscript {
    extra.set("kotlinVersion", "1.7.10")

    repositories {
        maven { url = uri("https://maven.google.com/") }
        maven { url = uri("https://jitpack.io") }
        gradlePluginPortal()
    }

    val kotlinVersion: String by extra
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.gms:google-services:4.3.14")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}