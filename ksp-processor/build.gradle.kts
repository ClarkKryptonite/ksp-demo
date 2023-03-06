plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":ksp-annotation"))
    implementation("com.google.devtools.ksp:symbol-processing-api:1.7.21-1.0.8")
    implementation("com.google.code.gson:gson:2.9.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}