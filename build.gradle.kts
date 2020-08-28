val javaFxVersion = "11.0.2"

plugins {
    java
    id("org.springframework.boot") version "2.3.3.RELEASE"
    id("io.spring.dependency-management") version "1.0.10.RELEASE"
    id("org.openjfx.javafxplugin") version "0.0.9"

    kotlin("jvm") version "1.4.0"
    kotlin("plugin.spring") version "1.4.0"
}
group = "io.atlassian"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
    jcenter()
}

extra["springCloudVersion"] = "Hoxton.SR7"
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")

    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation(kotlin("stdlib-jdk7"))
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.3.9")

    listOf("mac", "linux", "win").forEach {
        runtimeOnly("org.openjfx:javafx-graphics:$javaFxVersion:$it")
        runtimeOnly("org.openjfx:javafx-base:$javaFxVersion:$it")
        runtimeOnly("org.openjfx:javafx-controls:$javaFxVersion:$it")
    }

    implementation("com.amazonaws:aws-java-sdk-cloudwatch:1.11.842")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

javafx {
    version = javaFxVersion
    modules = listOf("javafx.controls", "javafx.graphics")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
        kotlinOptions.freeCompilerArgs = listOf("-Xjvm-default=compatibility")
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    test {
        useJUnitPlatform()
    }
}
