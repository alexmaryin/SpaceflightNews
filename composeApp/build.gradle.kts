import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()

    room {
        schemaDirectory("$projectDir/schemas")
    }

    sourceSets {
        androidMain.dependencies {
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.10.3")
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }
        commonMain.dependencies {
            implementation("org.jetbrains.compose.runtime:runtime:1.10.3")
            implementation("org.jetbrains.compose.foundation:foundation:1.10.3")
            implementation("org.jetbrains.compose.material3:material3:1.9.0")
            implementation(libs.material.icons)
            implementation("org.jetbrains.compose.material3:material3-adaptive-navigation-suite:1.9.0")
            implementation("org.jetbrains.compose.ui:ui:1.10.3")
            implementation("org.jetbrains.compose.components:components-resources:1.10.3")
            implementation("org.jetbrains.compose.ui:ui-tooling-preview:1.10.3")
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.paging.common)
            implementation(libs.androidx.paging.compose)
            api(libs.androidx.datastore)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.koin.compose.navigation)

            implementation(libs.navigation)

            implementation(libs.ktor.core)
            implementation(libs.ktor.cio)
            implementation(libs.ktor.content.negotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.kotlinx.serialization)
            implementation(libs.ktor.logging)
            implementation(libs.kotlinx.datetime)

            implementation(libs.coil.compose)
            implementation(libs.coil.network)

            implementation(libs.sqlite)
            implementation(libs.androidx.room.runtime)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

android {
    namespace = "ru.alexmaryin"
    compileSdkVersion(libs.versions.android.compileSdk.get().toInt())

    defaultConfig {
        applicationId = "ru.alexmaryin.spacenewsexplorer"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 5
        versionName = "1.3.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation("org.jetbrains.compose.ui:ui-tooling:1.10.3")
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspJvm", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)

}

compose.desktop {
    application {
        mainClass = "ru.alexmaryin.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SpaceNews Explorer"
            packageVersion = "1.3.1"
            modules("java.instrument", "java.management", "jdk.unsupported")

            windows {
                dirChooser = true
                perUserInstall = true
                iconFile.set(project.file("src/commonMain/composeResources/drawable/win-icon.ico"))
                menuGroup = "SpaceNews Explorer"
                upgradeUuid = "0bb4936d-d01a-40e9-8dca-a72f56c6719a"
            }
        }
    }
}
