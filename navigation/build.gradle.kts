@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    id(libs.plugins.kotlin.android.get().pluginId)
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}
apply {
    from("${rootProject.projectDir}/gradle/shared_build.gradle")
}

android {
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    // kotest
    testImplementation(libs.bundles.kotest)
    // mockk
    testImplementation(libs.mockk)
    implementation(libs.androidx.navigation.compose)
    implementation(project(":feature:authentication"))
    implementation(project(":feature:authentication-api"))

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}
