plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
    id(libs.plugins.kotlin.serialization.get().pluginId)
    kotlin("kapt")
}
apply {
    from("${rootProject.projectDir}/gradle/shared_build.gradle")
}

android {
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    buildFeatures {
        buildConfig = true
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
    // orbit
    implementation(libs.bundles.orbit)
    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // kotest
    testImplementation(libs.bundles.kotest)
    // navigation
    implementation(libs.androidx.navigation.compose)
    // mockk
    testImplementation(libs.mockk)
    // ktor
    implementation(libs.bundles.ktor)
    // logs (needed for ktor)
    implementation(libs.logback)
    // coil
    implementation(libs.coil)
    // projects
    implementation(project(":core-ui"))
    implementation(project(":core-testing"))
}

