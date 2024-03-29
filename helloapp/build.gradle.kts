import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.1.9.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("plugin.jpa") version "1.3.0"
	kotlin("jvm") version "1.3.0"
	kotlin("plugin.spring") version "1.3.0"
}

group = "com.galuszkat"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("io.micrometer:micrometer-registry-prometheus")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.h2database:h2:1.4.200")
	runtime("mysql:mysql-connector-java:8.0.17")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}