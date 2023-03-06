plugins {
    id("com.google.devtools.ksp")
    kotlin("jvm")
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":ksp-annotation"))
    ksp(project(":ksp-processor"))

    implementation(project(":submodule1"))
    implementation(project(":submodule2"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

