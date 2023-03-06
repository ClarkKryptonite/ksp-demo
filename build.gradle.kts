plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") apply false
}

group = "org.example"
version = "1.0-SNAPSHOT"

subprojects {
    repositories {
        mavenCentral()
    }
    plugins.whenPluginAdded {
        println("subprojects name:$name plugin:$this")
        when (this) {
            is ApplicationPlugin,
            is JavaLibraryPlugin -> {
                applyKspPluginAndDependencies()
                setSrcDir()
            }
            else -> {
                println("is Other Plugin")
            }
        }
    }
}

fun Project.applyKspPluginAndDependencies() {
    apply(plugin = "com.google.devtools.ksp")
    dependencies.add("implementation", (project(":ksp-annotation")))
    dependencies.add("ksp", (project(":ksp-processor")))
}

fun Project.setSrcDir() {
    sourceSets.names.forEach {
        setKspGeneratedFileSrc(it)
    }
}

fun Project.setKspGeneratedFileSrc(variantName: String) {
    sourceSets.main.configure {
        java.srcDir("$buildDir/generated/ksp/$variantName/kotlin")
    }
}

