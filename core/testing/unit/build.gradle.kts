plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.example.unit"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // Test
    api(libs.junit)
    api(libs.coroutines.test)
    api(libs.turbine)
    api(libs.mockk)
    api(libs.truth)
    api(libs.turbine)
}