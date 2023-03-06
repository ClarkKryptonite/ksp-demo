pluginManagement {
    plugins {
        id("com.google.devtools.ksp") version "1.7.21-1.0.8"
        kotlin("jvm") version "1.7.21"
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ksp-demo"
include("submodule1")
include("submodule2")
include("ksp-annotation")
include("ksp-processor")
include("main")
