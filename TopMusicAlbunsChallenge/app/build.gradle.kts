plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "pt.vilhena.topmusicalbunschallenge"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "pt.vilhena.topmusicalbunschallenge"
        minSdk = 31
        targetSdk = 36
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

    buildFeatures {
        viewBinding = true
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Retrofit
    implementation(libs.retrofit)

    // gson converter
    implementation(libs.converter.gson)

    // Jetpack Compose integration
    implementation(libs.androidx.navigation.compose)

    // Views/Fragments integration
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Moshi
    implementation(libs.moshi.kotlin)

    // Glide
    implementation(libs.glide)

    // Dagger2
    implementation(libs.dagger2)
    ksp(libs.dagger2.compiler)


    // Room DB
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)


    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.kotlinx.coroutines.test)

    // Mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    // LiveData testing
    testImplementation(libs.androidx.core.testing)

    // Retrofit
    testImplementation(libs.mockwebserver)

    // Room testing
    testImplementation(libs.androidx.room.testing)

    androidTestImplementation(libs.kotlinx.coroutines.test)
}