plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

android {
    namespace = "com.githukudenis.core_navigation"
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

    implementation(project(":feature_tasks"))
    implementation(project(":feature_statistics"))

    implementation(Dependencies.hilt_navigation_compose)
    implementation(Dependencies.compose_material)

    coreLibraryDesugaring(Dependencies.coreLibraryDesugar)

    implementation(Dependencies.core_test)
    implementation(Dependencies.android_test_mockito_android_test)
    implementation(Dependencies.compose_navigation)
    testImplementation(Dependencies.compose_ui_debug_android_test)
    androidTestImplementation(Dependencies.junit_compose_ui_test)
    androidTestImplementation(Dependencies.truth_android_test)
}