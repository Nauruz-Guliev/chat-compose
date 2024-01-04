plugins {
    alias(libs.plugins.androidLibrary)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
    alias(libs.plugins.detekt)
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
    implementation(libs.material3)
    implementation(libs.androidx.compose.material.material)
    implementation(libs.firebaseAnalytics)
    // mockk
    testImplementation(libs.mockk)
    implementation(libs.androidx.navigation.compose)
    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // projects
    implementation(project(":core-ui"))
    implementation(project(":feature:authentication"))
    implementation(project(":feature:authentication-api"))
    implementation(project(":feature:chat"))
    implementation(project(":feature:chat-api"))
    implementation(project(":feature:profile"))
    implementation(project(":feature:profile-api"))
    implementation(project(":feature:user-search"))
    implementation(project(":feature:user-search-api"))
    implementation(project(":feature:image-picker"))
}
