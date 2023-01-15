plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

android {
    namespace = "com.githukudenis.statistics"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    tasks.getByName("preBuild").dependsOn("ktlintFormat")

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    debug.set(true)
    verbose.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    disabledRules.set(setOf("final-newline", "no-wildcard-imports", "experimental:package-name", "annotation", "chain-wrapping"))
    reporters {
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
        reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
    }
}

dependencies {

    implementation(Dependencies.androidx_core)
    implementation(project(":core_design"))
    implementation(project(":core_data"))

    // lifecycle
    implementation(Dependencies.lifecycle_viewmodel)
    implementation(Dependencies.lifecycle_common)
    implementation(Dependencies.lifecycle_viewmodel_savedstate)
    implementation(Dependencies.lifecycle_runtime)
    implementation(Dependencies.lifecycle_runtime_compose)

    // coroutines
    implementation(Dependencies.coroutines_core)
    implementation(Dependencies.coroutines_android)

    // navigation
    implementation(Dependencies.compose_navigation)
    implementation(Dependencies.hilt_navigation_compose)

    // hilt
    implementation(Dependencies.hilt_work)
    testImplementation(Dependencies.junit)
    kapt(Dependencies.hilt_compiler)
    implementation(Dependencies.hilt_android)
    kapt(Dependencies.hilt_android_compiler)

    // datastore prefs
    implementation(Dependencies.datastore)

    implementation(Dependencies.savedstate)

    // api sdk version less than 24
    coreLibraryDesugaring(Dependencies.coreLibraryDesugar)

    // date time picker
    implementation(Dependencies.datepicker)

    // accompanist
    implementation(Dependencies.systemui_controller)

    // room
    implementation(Dependencies.room_runtime)
    implementation(Dependencies.room_ktx)
    implementation(Dependencies.room_paging)
    kapt(Dependencies.room_compiler)

    implementation(Dependencies.activity_compose)
    implementation(Dependencies.compose_ui)
    implementation(Dependencies.compose_tooling_preview)
    implementation(Dependencies.compose_animation)
    implementation(Dependencies.compose_ui_tooling)
    implementation(Dependencies.compose_material)

    // Local Unit Tests
    implementation(Dependencies.core_test)
    testImplementation(Dependencies.junit_test)
    testImplementation(Dependencies.hamcrest_test)
    testImplementation(Dependencies.core_testing)
    testImplementation(Dependencies.roboelectric_test)
    testImplementation(Dependencies.coroutines_test)
    testImplementation(Dependencies.truth_test)
    testImplementation(Dependencies.mockito_core)
    testImplementation(Dependencies.turbin_test)

    // Instrumented Unit Tests
    androidTestImplementation(Dependencies.junit_android_test)
    androidTestImplementation(Dependencies.mockito_dex_android_test)
    androidTestImplementation(Dependencies.android_coroutines_test)
    androidTestImplementation(Dependencies.core_testing_android_test)
    androidTestImplementation(Dependencies.truth_android_test)
    androidTestImplementation(Dependencies.ext_junit_android_test)
    androidTestImplementation(Dependencies.espresso_android_test)
    androidTestImplementation(Dependencies.mockito_core)
    androidTestImplementation(Dependencies.junit_compose_ui_test)
    androidTestImplementation(Dependencies.espresso_core_android_test)
    debugImplementation(Dependencies.compose_ui_debug_android_test)
    debugImplementation(Dependencies.compose_test_manifest_android_test)
}