apply {
    from("$rootDir/library-build.gradle")
}

dependencies{
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.weaponDomain))
    "implementation"(project(Modules.weaponDataSource))

    "implementation"(Kotlinx.coroutinesCore)

}