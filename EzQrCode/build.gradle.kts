import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "latest.release"
    application
}

group = "me.guest_3slo32w"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.zxing:core:3.4.1")
    implementation("com.google.zxing:javase:3.4.1")
    implementation("com.formdev:flatlaf:2.0.2")
    implementation("net.java.dev.jna:jna:5.9.0") // not using

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}

tasks {
    shadowJar {
        manifest {
            attributes(mapOf(
                "Main-Class" to "MainKt",              //will make your jar (produced by jar task) runnable
                "ImplementationTitle" to project.name,
                "Implementation-Version" to project.version)
            )
        }
    }
}