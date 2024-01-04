plugins {
    alias(libs.plugins.androidLibrary)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
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
    // mockk
    testImplementation(libs.mockk)
    //firebase
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebaseAuth)
    // navigation
    implementation(libs.androidx.navigation.compose)
    // projects
    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":core-testing"))
    implementation(project(":feature:authentication-api"))
    implementation(project(":feature:chat-api"))
}
