plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0"

    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.paging3compose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.paging3compose"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.material3)


    implementation(libs.ktor.client.android)
    implementation(libs.ktor.ktor.client.serialization)
    implementation(libs.kotlinx.serialization.json.v130)
    implementation(libs.ktor.client.logging.jvm)
    implementation(libs.ktor.ktor.client.serialization)


    implementation(libs.jetbrains.kotlinx.serialization.json)

    implementation(libs.ktor.client.gson)

    implementation(libs.coil.compose)

    implementation (libs.sdp.compose)

    // Paging 3
    implementation(libs.androidx.paging.compose)  //Use the latest stable version
    implementation(libs.androidx.paging.runtime.ktx)


    implementation(libs.haze.materials)

    // Hilt Dependencies
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Hilt with Navigation (For Fragments)
    implementation(libs.androidx.hilt.navigation.fragment)

    // Hilt ViewModel
    ksp("androidx.hilt:hilt-compiler:1.0.0")

    // Hilt for Navigation Component (if needed)
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // ViewModel & LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Activity KTX (For ViewModel delegation)
    implementation(libs.androidx.activity.ktx)

    // Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Room Coroutines Support
    implementation (libs.androidx.room.ktx)

    // Room Paging Support
    implementation (libs.androidx.room.paging)


}