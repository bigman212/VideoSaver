pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "VideoSaver"

include(":shared")
include(":app")
project(":shared").projectDir = File(rootProject.projectDir, "/VideoSaverSDK/shared")

