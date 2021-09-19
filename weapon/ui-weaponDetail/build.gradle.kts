apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    // UI Module specific dependencies here

    "implementation"(project(Modules.weaponInteractors))
    "implementation"(project(Modules.ui_weaponList))
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.weaponDomain))
}