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
    // orbit
    implementation(libs.bundles.orbit)
    // hilt
    implementation(libs.hilt.android)
    implementation(libs.firebase.database.ktx)
    kapt(libs.hilt.android.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    // kotest
    testImplementation(libs.bundles.kotest)
    // mockk
    testImplementation(libs.mockk)
    //firebase
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseAuth)
    implementation(libs.google.services)
    implementation(libs.modo.compose)
    implementation(libs.androidx.navigation.compose)
    //coil
    implementation(libs.coil)

    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":core-testing"))
    implementation(project(":feature:authentication-api"))
    implementation(project(":feature:image-picker"))
}
