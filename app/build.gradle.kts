@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("kotlin-parcelize")
}

android {
    namespace = "ru.kpfu.itis.gnt"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.kpfu.itis.gnt"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.6"
    }
    packaging {
        resources {
            excludes += "META-INF/*"
            pickFirsts += "lib/armeabi-v7a/libassmidi.so"
            pickFirsts += "lib/x86/libassmidi.so"
            excludes += "**/attach_hotspot_windows.dll"
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/licenses/ASM"
        }
    }
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
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.kotlin.reflect)
    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // firebase
    implementation(libs.modo.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(project(":feature:authentication"))
    implementation(project(":core-data"))
    implementation(project(":core-ui"))
    implementation(project(":navigation"))
}