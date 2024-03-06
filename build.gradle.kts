
import org.apache.tools.ant.filters.ReplaceTokens
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.diffplug.spotless") version "6.25.0"
    id("net.researchgate.release") version "3.0.2"
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
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.apache.commons:commons-lang3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

var gitHash = project.getGitSHA()
var releasing = false
gradle.taskGraph.whenReady {
    if (this.allTasks.contains(tasks.getByName("release")))
        {
            releasing = true
        }
}

tasks.named("bootBuildImage", BootBuildImage::class) {
    var tag = version.toString()
    if (!releasing) {
        tag += gitHash
    }
    imageName = "ghcr.io/fraucamidokura/spring_builder_tool/task-sample:$tag"
}

tasks.register<DockerPushTask>("bootPushImage") {
    group = "build"
    description = "Push image to docker hub"
    imageName = tasks.named("bootBuildImage", BootBuildImage::class).get().imageName
    dependsOn("bootBuildImage")
}

tasks.named("beforeReleaseBuild") {
    dependsOn("bootPushImage")
}
