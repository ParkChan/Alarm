object Version {
    const val gradle = "7.0.3"
    const val kotlin = "1.6.0"
    const val hilt = "2.40.5"
    const val junit5 = "1.8.0.0"

    const val appcompat = "1.3.1"
    const val coreKtx = "1.6.0"
    const val activityKtx = "1.3.1"
    const val fragmentKtx = "1.3.6"
    const val material = "1.1.0"
    const val constraintLayout = "2.1.2"
    const val recyclerView = "1.2.1"
    const val room = "2.3.0"
    const val navigation = "2.3.5"

    const val coroutine = "1.5.2"

    const val jupiter = "5.8.2"
    const val assertjCore = "3.21.0"
    const val mockk = "1.10.6"
    const val turbine = "0.7.0"
    const val junit = "4.13.2"
    const val extJunit = "1.1.3"
    const val espresso = "3.4.0"

    const val androidTestCore = "1.3.0"
    const val androidTestRunner = "1.3.0"

    const val timber = "5.0.1"

    const val gradleVersionsPlugin = "0.38.0"

}

object ProjectConfig {
    const val gradle = "com.android.tools.build:gradle:${Version.gradle}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
    const val androidJunit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Version.junit5}"
    const val hiltAndroidGradlePlugin =
        "com.google.dagger:hilt-android-gradle-plugin:${Version.hilt}"
    const val gradleVersionPlugin =
        "com.github.ben-manes:gradle-versions-plugin:${Version.gradleVersionsPlugin}"
    const val navigationSafeArgsGradlePlugin =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Version.navigation}"
}

object AndroidConfig {
    const val compileSdk = 30
    const val minSdk = 26
    const val targetSdk = 30
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val runnerBuilder = "runnerBuilder"
    const val androidJunitRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val androidJunit5Builder = "de.mannodermaus.junit5.AndroidJUnit5Builder"

}

object CoroutineConfig {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
}

object TestConfig {
    const val junitJupiterApi = "org.junit.jupiter:junit-jupiter-api:${Version.jupiter}"
    const val junitJupiterEngine = "org.junit.jupiter:junit-jupiter-engine:${Version.jupiter}"
    const val assertjCore = "org.assertj:assertj-core:${Version.assertjCore}"

    const val androidTestCore =
        "de.mannodermaus.junit5:android-test-core:${Version.androidTestCore}"
    const val androidTestRunner =
        "de.mannodermaus.junit5:android-test-runner:${Version.androidTestRunner}"

    const val mockk = "io.mockk:mockk:${Version.mockk}"

    const val turbine = "app.cash.turbine:turbine:${Version.turbine}"

    const val junit = "junit:junit:${Version.junit}"
    const val extJunit = "androidx.test.ext:junit:${Version.extJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"

    const val roomTesting = "androidx.room:room-testing:${Version.room}"
}

object AndroidXConfig {

    const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
    const val coreKtx = "androidx.core:core-ktx:${Version.coreKtx}"
    const val material = "com.google.android.material:material:${Version.material}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"

    const val activityKtx = "androidx.activity:activity-ktx:${Version.activityKtx}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Version.fragmentKtx}"

    const val recyclerView = "androidx.recyclerview:recyclerview:${Version.recyclerView}"

    const val roomKtx = "androidx.room:room-ktx:${Version.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Version.room}"

    const val navigationFragmentKtx =
        "androidx.navigation:navigation-fragment-ktx:${Version.navigation}"
    const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:${Version.navigation}"

}

object GoogleConfig {
    const val material = "com.google.android.material:material:${Version.material}"
}

object DaggerHiltConfig {
    const val android = "com.google.dagger:hilt-android:${Version.hilt}"

    const val core = "com.google.dagger:hilt-core:${Version.hilt}"
    const val compiler = "com.google.dagger:hilt-compiler:${Version.hilt}"
}

object LogConfig {
    const val timber = "com.jakewharton.timber:timber:${Version.timber}"
}