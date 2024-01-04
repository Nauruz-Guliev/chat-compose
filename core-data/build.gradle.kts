plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.android)
    id(libs.plugins.dagger.hilt.get().pluginId)
    alias(libs.plugins.detekt)
    kotlin("kapt")
}

android {
    namespace = "ru.kpfu.itis.core_data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.firebase.database.ktx)
    //firebase
    implementation(platform(libs.firebaseBom))
    implementation(libs.firebaseAuth)
    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // datastore
    implementation(libs.androidx.datastore.preferences)
}