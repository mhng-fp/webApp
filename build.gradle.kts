import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    distribution
    kotlin("multiplatform") version "2.4.0"
    kotlin("plugin.serialization") version "2.1.10"
}


group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        mainRun {
            mainClass = "org.example.backend.ServerKt"
        }
        compilerOptions {
            jvmTarget = JvmTarget.JVM_1_8
        }
    }

    js {
        browser {
            binaries.executable()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.5.0")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:3.5.0")
                implementation("io.ktor:ktor-server-netty:3.5.0")
                implementation("io.ktor:ktor-server-content-negotiation:3.5.0")
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.5.0")
                implementation("io.ktor:ktor-server-cors-jvm:3.5.0")
                implementation("io.ktor:ktor-server-compression-jvm:3.5.0")
            }
        }
        val jvmTest by getting {
            dependencies {
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:3.5.0")
                implementation("io.ktor:ktor-client-content-negotiation:3.5.0")
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.5.0")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:2026.5.4-19.2.6")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:2026.5.4-19.2.6")
            }
        }
        val jsTest by getting {
            dependencies {
            }
        }
    }
}


// 1. Prevent implicit dependency tracking errors across all variants
tasks.named("jsBrowserDevelopmentWebpack").configure {
    mustRunAfter(tasks.named("jsProductionExecutableCompileSync"))
    mustRunAfter(tasks.named("jsDevelopmentExecutableCompileSync"))
}

tasks.named("jsBrowserProductionWebpack").configure {
    mustRunAfter(tasks.named("jsProductionExecutableCompileSync"))
    mustRunAfter(tasks.named("jsDevelopmentExecutableCompileSync"))
}

// 2. Include JS artifacts in any JAR we generate (Dev only)
tasks.named<Jar>("jvmJar").configure {
    val webpackTask = tasks.named<KotlinWebpack>("jsBrowserDevelopmentWebpack")
    dependsOn(webpackTask)

    from(webpackTask.flatMap { it.mainOutputFile }) {
        into("static")
    }
}

// 3. Keep this so your local running server can find and serve the JS files
tasks.withType<JavaExec>().configureEach {
    classpath(tasks.named<Jar>("jvmJar"))
}


distributions {
    main {
        contents {
            // Depend on the task provider instead of the raw file path
            from(tasks.named("jvmJar")) {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}