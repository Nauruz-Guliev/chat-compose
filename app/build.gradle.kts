plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.kotlin.android)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.dagger.hilt.get().pluginId)
    id(libs.plugins.google.services.get().pluginId)
    id(libs.plugins.google.firebase.perf.get().pluginId)
    alias(libs.plugins.detekt)
    id("com.google.firebase.crashlytics")
    kotlin("kapt")
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(libs.kotlin.reflect)
    // hilt
    implementation(libs.hilt.android)
    implementation(libs.firebase.messaging)
    kapt(libs.hilt.android.compiler)
    // google
    implementation(libs.play.services.measurement.api)
    implementation(libs.firebase.perf) {
        // гугл сервисы используют старую версию протобафа, которая конфликтует с firebase perf
        exclude("com.google.firebase", "protolite-well-known-types")
        exclude("com.google.protobuf", "protobuf-javalite")
    }
    //navigation
    implementation(libs.androidx.navigation.compose)
    // projects
    implementation(project(":feature:authentication"))
    implementation(project(":core-data"))
    implementation(project(":core-ui"))
    implementation(project(":navigation"))
}