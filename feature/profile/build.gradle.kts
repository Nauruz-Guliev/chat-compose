plugins {
    alias(libs.plugins.androidLibrary)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
    kotlin("kapt")
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
    kapt(libs.hilt.android.compiler)
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    // kotest
    testImplementation(libs.bundles.kotest)
    // mockk
    testImplementation(libs.mockk)
    // firebase
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebaseAuth)
    // navigation
    implementation(libs.androidx.navigation.compose)
    // coil
    implementation(libs.coil)
    // projects
    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":core-testing"))
    implementation(project(":feature:authentication-api"))
    implementation(project(":feature:image-picker"))
}
