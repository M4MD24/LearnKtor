val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.3.0"
    id("io.ktor.plugin") version "3.4.0"
    kotlin("plugin.serialization") version "2.3.0"
}

group = "com.barmajaa"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-content-negotiation:3.4.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.0")
    implementation("io.ktor:ktor-server-resources:3.4.0")
    implementation("io.ktor:ktor-server-host-common:3.4.0")
    implementation("io.ktor:ktor-server-status-pages:3.4.0")
    implementation("io.ktor:ktor-server-request-validation:3.4.0")
    implementation("io.ktor:ktor-server-rate-limit:3.4.0")
    implementation("io.ktor:ktor-server-auto-head-response:3.4.0")
    implementation("io.ktor:ktor-server-partial-content:3.4.0")
    implementation("io.ktor:ktor-server-auth:3.4.0")
    implementation("io.ktor:ktor-server-sessions:3.4.0")
    implementation("io.ktor:ktor-client-core:3.4.0")
    implementation("io.ktor:ktor-client-cio:3.4.0")
    implementation("io.ktor:ktor-client-content-negotiation:3.4.0")
    implementation("io.ktor:ktor-server-auth-jwt:3.4.0")
    implementation("io.ktor:ktor-server-sse:3.4.0")
    implementation("io.ktor:ktor-server-websockets:3.4.0")
    implementation("io.ktor:ktor-server-call-logging:3.4.0")
    implementation("org.jetbrains.exposed:exposed-core:0.61.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.61.0")
    implementation("com.h2database:h2:2.3.232")
    implementation("org.postgresql:postgresql:42.7.10")
    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.61.0")
    implementation("org.jetbrains.exposed:exposed-json:0.61.0")
    implementation("org.mongodb:mongodb-driver-core:4.10.2")
    implementation("org.mongodb:mongodb-driver-sync:4.10.2")
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.2")
    implementation("org.mongodb:bson:4.10.2")
    implementation("org.mongodb:bson-kotlinx:4.10.2")
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}