plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("it.skrape:skrapeit:1.2.2")
    implementation("it.skrape:skrapeit:0-SNAPSHOT") { isChanging = true }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}