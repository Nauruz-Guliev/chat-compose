@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}
apply {
    from("${rootProject.projectDir}/gradle/shared_build.gradle")
}

android {
    tasks.withType<Test> {
        useJUnitPlatform()
    }
    buildTypes {
        debug {
            buildConfigField("String", "IMAGE_SEARCH_URL", "\"api.unsplash.com/\"")
            buildConfigField("String", "IMAGE_API_KEY", "\"26_aU5jMAyUrefrXkWr3ilmtowksurXPOx2_gahT2Dw\"")
        }
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.android)
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    // kotest
    testImplementation(libs.bundles.kotest)
    // mockk
    testImplementation(libs.mockk)
    // ktor
    implementation(libs.bundles.ktor)
    implementation ("ch.qos.logback:logback-classic:1.2.3")
    // coil
    implementation(libs.coil)

    implementation(libs.androidx.navigation.compose)
    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":core-testing"))
}
