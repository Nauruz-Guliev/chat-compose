buildscript {
    dependencies {
        classpath(libs.google.services)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10" apply true
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.jvm) apply false
}
true // Needed to make the Suppress annotation work for the plugins block