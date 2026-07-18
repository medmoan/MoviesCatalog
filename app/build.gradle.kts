import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.netflixclone"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.netflixclone"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val localProperties = Properties().apply {
            load(rootProject.file("local.properties").inputStream())
        }
        val tmdbApiKey = localProperties.getProperty("TMDB_API_KEY")
        buildConfigField(
            "String",
            "TMDB_API_KEY",
            "\"$tmdbApiKey\""
        )
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation("com.valentinilk.shimmer:compose-shimmer:1.3.3")
    implementation(libs.androidx.compose.runtime.saveable)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android)
    implementation(libs.errorprone.annotations)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.room3.external.antlr)
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.room.ktx)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.lifecycle.viewmodel.compose.android)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}