import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.11"
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
}

taboolib {
    env {
        // 安装模块
        install(UNIVERSAL,
            BUKKIT,
            NMS,
            DATABASE,
            EXPANSION_PLAYER_DATABASE,
            EXPANSION_PTC_OBJECT,
            UI,
            BUKKIT_ALL,
            KETHER,
            BUKKIT_HOOK)
    }
    version { taboolib = "6.1.2-beta10" }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
    compileOnly("ink.ptms.core:v12004:12004:mapped")
    compileOnly("ink.ptms.core:v12004:12004:universal")
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
    implementation(kotlin("reflect"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xjvm-default=all")
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
