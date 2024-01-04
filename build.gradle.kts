import io.gitlab.arturbosch.detekt.Detekt

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
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.google.firebase.perf) apply false
    alias(libs.plugins.kotlin.serialization) apply true
}

tasks.register("detektAll") {
    allprojects {
        this@register.dependsOn(tasks.withType<Detekt>())
    }
}
true // Needed to make the Suppress annotation work for the plugins block