
object Versions {
    val arch_core_testing = "2.1.0"
    val core_ktx: String = "1.9.0"
    val lifecycle = "2.5.1"
    val coroutines = "1.6.4"
    val navigation_compose = "2.5.3"
    val hilt_navigation_compose = "1.0.0"
    val runtime_compose = "2.6.0-alpha03"
    val room = "2.4.3"
    val hilt_compiler = "1.0.0"
    val savedstate = "1.2.0"
    val hilt_work = "1.0.0"
    val junit = "4.13.2"
    val hilt = "2.44.2"
    val datastore = "1.0.0"
    val compose = "1.3.2"
    val activity_compose = "1.6.1"
    val core_testing = "1.5.0"
    val date_picker = "0.8.1-rc"
    val hamcrest = "1.3"
    val roboelectric = "4.9"
    val desugaring = "2.0.0"
    val truth = "1.1.3"
    val mockito = "4.8.0"
    val mockito_dex = "2.28.3"
    val turbine = "0.12.1"
    val junit_test = "1.1.4"
    val system_ui_controller = "0.28.0"
    val compose_material = "1.0.1"
    val espresso = "3.5.0"
    val accompanist_version = "0.28.0"
    val android_core = "1.9.0"
}
object Dependencies {

    //lifecyle
    val androidx_core = "androidx.core:core:${Versions.android_core}"
    val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val lifecycle_common = "androidx.lifecycle:lifecycle-common:${Versions.lifecycle}"
    val lifecycle_viewmodel_savedstate = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    val lifecycle_runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    val lifecycle_runtime_compose =
        "androidx.lifecycle:lifecycle-runtime-compose:${Versions.runtime_compose}"

    // coroutines
    val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"


// navigation
    val compose_navigation = "androidx.navigation:navigation-compose:${Versions.navigation_compose}"
    val hilt_navigation_compose = "androidx.hilt:hilt-navigation-compose:${Versions.hilt_navigation_compose}"

// hilt
    val hilt_work = "androidx.hilt:hilt-work:${Versions.hilt_work}"
    val junit = "junit:junit:${Versions.junit}"
    val hilt_compiler = "androidx.hilt:hilt-compiler:${Versions.hilt_compiler}"
    val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt}"
    val hilt_android_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

// datastore prefs
    val datastore = "androidx.datastore:datastore-preferences-core:${Versions.datastore}"

    val savedstate = "androidx.savedstate:savedstate-ktx:${Versions.savedstate}"

// api sdk version less than 24
    val coreLibraryDesugar = "com.android.tools:desugar_jdk_libs:${Versions.desugaring}"

// date time picker
    val datepicker = "io.github.vanpra.compose-material-dialogs:datetime:${Versions.date_picker}"

// insets
    val systemui_controller = "com.google.accompanist:accompanist-systemuicontroller:${Versions.system_ui_controller}"

    // accompanist drawable painter
    val drawable_painter = "com.google.accompanist:accompanist-drawablepainter:${Versions.accompanist_version}"

// room
    val room_runtime = "androidx.room:room-runtime:${Versions.room}"
    val room_ktx = "androidx.room:room-ktx:${Versions.room}"
    val room_paging = "androidx.room:room-paging:${Versions.room}"
    val room_compiler = "androidx.room:room-compiler:${Versions.room}"

    //accompanist permissions
    val compose_accompanist_permissions = "com.google.accompanist:accompanist-permissions:${Versions.accompanist_version}"


    val activity_compose = "androidx.activity:activity-compose:${Versions.activity_compose}"
    val compose_ui = "androidx.compose.ui:ui:${Versions.compose}"
    val compose_tooling_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    val compose_animation = "androidx.compose.animation:animation:${Versions.compose}"
    val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    val compose_material = "androidx.compose.material3:material3:${Versions.compose_material}"

// Local Unit Tests
    val core_test = "androidx.test:core:${Versions.core_testing}"
    val junit_test = "junit:junit:${Versions.junit}"
    val hamcrest_test = "org.hamcrest:hamcrest-all:${Versions.hamcrest}"
    val core_testing = "androidx.arch.core:core-testing:${Versions.arch_core_testing}"
    val roboelectric_test = "org.robolectric:robolectric:${Versions.roboelectric}"
    val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    val truth_test = "com.google.truth:truth:${Versions.truth}"
    val mockito_core = "org.mockito:mockito-core:${Versions.mockito}"
    val turbin_test = "app.cash.turbine:turbine:${Versions.turbine}"

// Instrumented Unit Tests
    val junit_android_test = "junit:junit:${Versions.junit}"
    val mockito_dex_android_test = "com.linkedin.dexmaker:dexmaker-mockito:${Versions.mockito_dex}"
    val android_coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    val core_testing_android_test = "androidx.arch.core:core-testing:${Versions.arch_core_testing}"
    val truth_android_test = "com.google.truth:truth:${Versions.truth}"
    val ext_junit_android_test = "androidx.test.ext:junit:${Versions.junit_test}"
    val espresso_core_android_test = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val android_test_mockito_android_test = "org.mockito:mockito-core:${Versions.mockito}"
    val espresso_android_test = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val junit_compose_ui_test = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    val compose_ui_debug_android_test = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    val compose_test_manifest_android_test = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
}