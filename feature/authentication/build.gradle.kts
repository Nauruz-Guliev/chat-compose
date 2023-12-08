@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
}
apply {
    from("${rootProject.projectDir}/gradle/shared_build.gradle")
}
apply {
    from("${rootProject.projectDir}/gradle/feature_dependency.gradle")
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
    // kotest
    testImplementation(libs.bundles.kotest)
    testImplementation(libs.mockk)
}