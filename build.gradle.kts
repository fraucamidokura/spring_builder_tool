
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.diffplug.spotless") version "6.25.0"
    id("net.researchgate.release")  version "3.0.2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

tasks.withType<ProcessResources> {
    filesMatching("**/banner.txt") {
        filter<ReplaceTokens>("tokens" to mapOf("project.version" to project.version))
    }
}

spotless {
    java {
        googleJavaFormat()
    }
    kotlin {
        target("buildSrc/src/main/kotlin/**")
        ktfmt()
    }
    kotlinGradle {
        ktlint()
    }
}

tasks.named("spotlessCheck").configure {
    dependsOn("spotlessApply")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.apache.commons:commons-lang3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

var gitHash = "This is just a simple tests"
var releasing = false
gradle.taskGraph.whenReady{
    if(this.allTasks.contains(tasks.getByName("release"))){
        releasing = true
    }
}


tasks.register<DockerPushTask>("bootPushImage") {
    imageName = gitHash
}

tasks.named("beforeReleaseBuild"){
    dependsOn("bootPushImage")
}