val kotlinVersion: String by project
val http4kVersion: String by project
val junitVersion: String by project
val arrowVersion: String by project
val nitriteVersion: String by project

plugins {
  id("com.diffplug.spotless") version "6.18.0"
  application
}

group = "binusgdc.com"

repositories { mavenCentral() }

dependencies {
  implementation("io.javalin:javalin:5.5.0")
  implementation("org.slf4j:slf4j-simple:2.0.7")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

application { mainClass.set("com.binusgdc.strongholde.Main") }

tasks.named<Test>("test") { useJUnitPlatform() }

configure<com.diffplug.gradle.spotless.SpotlessExtension> {
  java {
    importOrder()
    removeUnusedImports()
    googleJavaFormat()
  }
  kotlinGradle {
    target("*.gradle.kts")
    ktfmt()
  }
}
