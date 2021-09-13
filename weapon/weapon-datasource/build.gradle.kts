apply {
    from("$rootDir/library-build.gradle")
}

plugins {
    kotlin(KotlinPlugins.serialization) version Kotlin.version
    id(SqlDelight.plugin)
}

dependencies {
    "implementation"(project(Modules.weaponDomain))

    "implementation"(Ktor.core)
    "implementation"(Ktor.clientSerialization)
    "implementation"(Ktor.android)

    "implementation"(SqlDelight.runtime)

    sqldelight {
        database("WeaponDatabase") {
            packageName = "com.sk.weapon_datasource.cache"
            sourceFolders = listOf("sqldelight")
        }
    }
}