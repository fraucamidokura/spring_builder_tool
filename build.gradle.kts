import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.diffplug.spotless") version "6.25.0"
    id("net.researchgate.release") version "3.0.2"
    id("io.gatling.gradle") version "3.12.0.2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

val cucumberVersion = "7.20.0"
val commonsCliVersion = "1.9.0"
val galtlingVersion = "3.12.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.postgresql:postgresql")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.apache.commons:commons-lang3")
    testImplementation("commons-cli:commons-cli:$commonsCliVersion")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("io.gatling.highcharts:gatling-charts-highcharts:$galtlingVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

sourceSets.getByName("test") {
    java.srcDir("src/test/java")
    java.srcDir("src/test_e2e/java")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
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

tasks.withType<ProcessResources> {
    filesMatching("**/application.properties") {
        expand(project.properties)
    }
}

tasks.named("spotlessCheck").configure {
    dependsOn("spotlessApply")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

var gitHash = project.getGitSHA()
var releasing = false
gradle.taskGraph.whenReady {
    if (this.allTasks.contains(tasks.getByName("release"))) {
        releasing = true
    }
}
var tag = version.toString()
if (!releasing) {
    tag += gitHash
}
var globalImageName = "ghcr.io/fraucamidokura/spring_builder_tool/task-sample:$tag"

tasks.named("bootBuildImage", BootBuildImage::class) {
    imageName = globalImageName
}

tasks.register<DockerPushTask>("bootPushImage") {
    group = "build"
    description = "Push image to docker hub"
    imageName = globalImageName
    dependsOn("bootBuildImage")
}

tasks.named("beforeReleaseBuild") {
    dependsOn("bootPushImage")
}

tasks.register<ClusterCreateTask>("bootRunInCluster") {
    group = "application"
    description = "Run the application into local kind cluster"
    dependsOn("bootBuildImage")
    imageName = globalImageName
    imageTag = tag
}

tasks.register<JavaExec>("cucumberTest") {
    group = "verification"
    description = "Run cucumber tests in the cluster"
    dependsOn("compileTestJava", "bootRunInCluster")
    classpath = sourceSets["test"].runtimeClasspath
    mainClass = "cucumber.runner.RunCucumber"
    args(
        "--url",
        "http://localhost:8080",
        "--glue",
        "cucumber.steps",
        "src/test_e2e/features",
    )
}

tasks.register("runGatling") {
    group = "verification"
    description = "Run load tests"
    dependsOn("bootRunInCluster", "gatlingRun")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
