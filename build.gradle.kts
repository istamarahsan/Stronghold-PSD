import org.gradle.kotlin.dsl.version

val junitVersion: String by project
val jooqVersion: String by project

plugins {
  id("com.diffplug.spotless") version "6.18.0"
  application
}

group = "notbinusgdc.com"

repositories { mavenCentral() }

dependencies {
  implementation("com.zaxxer:HikariCP:5.0.1")
  implementation("org.mariadb.jdbc:mariadb-java-client:3.1.4")
  implementation("io.javalin:javalin:5.5.0")
  implementation("org.slf4j:slf4j-simple:2.0.7")
  implementation("org.jooq:jooq:$jooqVersion")
  implementation("org.jooq:jooq-codegen:$jooqVersion")
  implementation("org.jooq:jooq-meta:$jooqVersion")
  implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
  implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.1")
  testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

application { mainClass.set("com.notbinusgdc.stronghold.Main") }

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
