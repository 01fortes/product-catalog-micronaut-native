import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

plugins {
    id("io.micronaut.application") version "4.5.3"
    id("com.gradleup.shadow") version "8.3.6"
    id("io.micronaut.aot") version "4.5.3"
}

version = "0.1"
group = "com.jsamkt.learn"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut:micronaut-http-validation")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    compileOnly("io.micronaut:micronaut-http-client")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.yaml:snakeyaml")

    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.sql:micronaut-vertx-pg-client")

    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut.tracing:micronaut-tracing-opentelemetry-annotation")
    implementation("io.micronaut.tracing:micronaut-tracing-opentelemetry-http")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp")
    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-otlp")

}


application {
    mainClass = "com.jsamkt.learn.Application"
}
java {
    sourceCompatibility = JavaVersion.toVersion("23")
    targetCompatibility = JavaVersion.toVersion("23")
}


graalvmNative.toolchainDetection = false

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.jsamkt.learn.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }
}

graalvmNative {
    binaries {
        named("main") {
            imageName = "product-catalog-micronaut-native"
            buildArgs.add("--initialize-at-build-time=kotlin.coroutines.intrinsics.CoroutineSingletons")
        }
    }
}

tasks.named<DockerBuildImage>("dockerBuild") {
    images.set(listOf("product-catalog-micronaut-native"))
}