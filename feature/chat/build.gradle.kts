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
    implementation(libs.appcompat)
    implementation(libs.androidx.constraintlayout)
    // hilt
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    // kotest
    testImplementation(libs.bundles.kotest)
    // mockk
    testImplementation(libs.mockk)
    //firebase
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseAuth)
    implementation(libs.google.services)
    implementation(libs.androidx.navigation.compose)
    //coil
    implementation(libs.coil)
    // constraint
    implementation(libs.androidx.constraintlayout.compose)
    // room
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    // projects
    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":core-testing"))
    implementation(project(":feature:chat-api"))
}
