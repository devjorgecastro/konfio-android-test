import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.org.jetbrains.kotlinx.kover)
    alias(libs.plugins.org.sonarqube)
}

fun getLocalProperties(): Properties {
    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        FileInputStream(localPropertiesFile).use { fileInputStream ->
            properties.load(fileInputStream)
        }
    }
    return properties
}

android {
    namespace = "com.example.konfio.android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.konfio.android"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"https://jsonblob.com/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"https://jsonblob.com/\"")
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
        buildConfig = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

tasks.named("sonar") {
    dependsOn("koverXmlReportDebug")
}

sonar {
    val properties = getLocalProperties()
    properties {
        property("sonar.projectKey", properties.getProperty("SONAR_PROJECT_KEY"))
        property("sonar.organization", "Konfio")
        property("sonar.projectName", properties.getProperty("SONAR_PROJECT_NAME"))
        property("sonar.host.url", properties.getProperty("SONAR_HOST_URL"))
        property("sonar.sourceEncoding", "UTF-8")

        val koverReportPath = layout.buildDirectory.file("reports/kover/reportDebug.xml").get().asFile.absolutePath
        property("sonar.coverage.jacoco.xmlReportPaths", koverReportPath)
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
    testImplementation(libs.junit5.jupiter.api)
    testRuntimeOnly(libs.junit5.jupiter.engine)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.hilt.android.testing)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.hilt.navigation.compose) {
        exclude(group = "androidx.navigation", module = "navigation-compose")
    }
    implementation(libs.androidx.navigation.compose)

    implementation(libs.coil3.compose)
    implementation(libs.coil3.network.okhttp)

    implementation(libs.kotlin.coroutines.android)
    testImplementation(libs.kotlin.coroutines.test)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    // Ktor
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.core)
}