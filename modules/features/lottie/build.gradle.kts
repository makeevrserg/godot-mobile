@file:Suppress("UnusedPrivateMember")

import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo


plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("ru.astrainteractive.gradleplugin.java.core")
    id("ru.astrainteractive.gradleplugin.android.core")
    alias(libs.plugins.klibs.gradle.android.namespace)
    alias(libs.plugins.kotlin.compose.gradle)
}

android.namespace = "${requireProjectInfo.group}.features.lottie"

kotlin {
    applyDefaultHierarchyTemplate()
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    iosSimulatorArm64()
    sourceSets {
        val androidMain by getting {
            dependencies {
            }
        }
    }
}

dependencies {
    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material:material")
    implementation("androidx.fragment:fragment:1.8.8")
    implementation("androidx.fragment:fragment-ktx:1.8.8")
    implementation("androidx.fragment:fragment-compose:1.8.8")
}
