import org.gradle.wrapper.Install
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

buildscript {
  val kotlinVersion: String by extra

  repositories {
    mavenLocal()
    jcenter()
    mavenCentral()
  }

  dependencies {
    classpath(kotlin("gradle-plugin:$kotlinVersion"))
  }
}

group = "selenium-runner"
version = "0.1"

apply {
  plugin("java")
  plugin("kotlin")
}

plugins {
  java
  maven
  `maven-publish`
}

repositories {
  mavenLocal()
  jcenter()
  mavenCentral()
  maven {
    setUrl("https://jitpack.io")
  }
}

val kotlinVersion: String by extra
val kotlinCoroutinesVersion: String by extra
dependencies {
  compile(kotlin("stdlib", kotlinVersion))
  compile(kotlin("stdlib-jdk8", kotlinVersion))
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
  compile("org.seleniumhq.selenium:selenium-chrome-driver:3.11.0")

  compile("io.github.bonigarcia:webdrivermanager:2.2.1")
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}
configure<KotlinProjectExtension> {
  experimental.coroutines = Coroutines.ENABLE
}

task<Wrapper>("gradleWrapper") {
  gradleVersion = "4.6"
  distributionType = Wrapper.DistributionType.ALL
}

val sourcesJar by tasks.creating(Jar::class) {
  dependsOn += "classes"

  classifier = "sources"
  from(the<JavaPluginConvention>().sourceSets["main"].allSource)
}

artifacts {
  add("archives", sourcesJar)
}

configure<PublishingExtension> {
  publications.create<MavenPublication>("SeleniumRunnerPublication") {
    from(components["java"])
    artifact(sourcesJar)

    groupId = "deeprim"
    artifactId = "selenium-runner"
    version = "0.1"

    pom.withXml {
      asNode().apply {
        appendNode("description", "Parallel Selenium Runner")
        appendNode("name", "selenium-runner")
        appendNode("url", "https://github.com/deeprim/selenium-runner")

        val license = appendNode("licenses").appendNode("license")
        license.appendNode("name", "The Apache Software License, Version 2.0")
        license.appendNode("url", "http://www.apache.org/licenses/LICENSE-2.0.txt")
        license.appendNode("distribution", "repo")

        val developer = appendNode("developers").appendNode("developer")
        developer.appendNode("id", "Dmytriy Primshyts")
        developer.appendNode("name", "Dmytriy Primshyts")
        developer.appendNode("email", "dprimshyts@gmail.com")

        appendNode("scm").appendNode("url", "https://github.com/deeprim/selenium-runner")
      }
    }
  }
}
