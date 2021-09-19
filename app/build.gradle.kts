plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Android.compileSdk
    buildToolsVersion = Android.buildTools

    defaultConfig {
        applicationId = Android.appId
        minSdk = Android.minSdk
        targetSdk = Android.targetSdk
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = AndroidXTest.runner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
}

dependencies {
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)

    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.material)
    implementation(Compose.tooling)

    //navigation dependencies
    implementation(Compose.navigation)
    implementation(Compose.hiltNavigation)  //helps to create viewModels on a per backstackentry basis

    implementation(Google.material)
    implementation(Coil.coil)


    implementation(Hilt.android)
    kapt(Hilt.compiler)


    "implementation"(project(Modules.core))
    "implementation"(project(Modules.weaponDomain))
    "implementation"(project(Modules.weaponInteractors))
    "implementation"(project(Modules.ui_weaponList))
    "implementation"(project(Modules.ui_weaponDetail))

    "implementation"(SqlDelight.androidDriver)

}