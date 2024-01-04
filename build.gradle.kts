buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath(libs.gradle)
        classpath(libs.perf.plugin) {
            configurations.all {
                // пришлось исключить конфликтные зависимости
                exclude("com.google.firebase", "protolite-well-known-types")
                exclude("com.google.protobuf", "protobuf-javalite")
            }
        }
    }
}

apply(from = "$rootDir/ktlint.gradle")

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10" apply true
}
true // Needed to make the Suppress annotation work for the plugins block