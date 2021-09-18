
apply{
    from("$rootDir/android-library-build.gradle")
}

dependencies{
    // UI Module specific dependencies here
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.weaponDomain))
    "implementation"(project(Modules.weaponInteractors))
    "implementation"(Coil.coil)

    "implementation"(SqlDelight.androidDriver)
}