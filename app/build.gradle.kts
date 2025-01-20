plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    kotlin("kapt")
    kotlin("plugin.serialization") version "2.0.21"
}

android {
    namespace = "com.github.crisacm.todotesting"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.github.crisacm.todotesting"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.github.crisacm.todotesting.CustomTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":core-data"))
    implementation(project(":core-domain"))
    testImplementation(project(":core-database"))
    testImplementation(project(":core-domain"))
    testImplementation(project(":core-network"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // JUnit
    testImplementation(libs.junit)
    implementation(libs.androidx.junit.ktx)
    androidTestImplementation(libs.androidx.junit)
    // Arch Core
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.androidx.core.testing)
    // Espresso
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.test:core:1.6.1")
    androidTestImplementation("androidx.test:runner:1.6.2")
    androidTestImplementation("androidx.test:rules:1.6.1")
    // Hamcrest
    testImplementation("org.hamcrest:hamcrest:2.2")
    // Truth
    testImplementation("com.google.truth:truth:1.4.4")
    // Mockito
    testImplementation("org.mockito:mockito-core:5.6.0")
    androidTestImplementation("org.mockito:mockito-android:5.15.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    // Robolectric
    testImplementation("org.robolectric:robolectric:4.10")
    // Coroutines
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    // Orchestrator
    androidTestUtil("androidx.test:orchestrator:1.5.1")

    // Splashscreen Core
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.48")

    // Startup
    implementation("androidx.startup:startup-runtime:1.2.0")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment:2.8.5")
    implementation("androidx.navigation:navigation-ui:2.8.5")
    androidTestImplementation("androidx.navigation:navigation-testing:2.8.5")
    debugImplementation("androidx.fragment:fragment-testing:1.8.5")

    // Room (Only for testing)
    ksp("androidx.room:room-compiler:2.6.1")
    testImplementation("androidx.room:room-testing:2.6.1")

    // JSON serialization library, works with the Kotlin serialization plugin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}