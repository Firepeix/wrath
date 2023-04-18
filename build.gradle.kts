import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    val kotlinVersion: String by System.getProperties()
    kotlin("js") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
}

version = "1.0.0-SNAPSHOT"
group = "com.tutu.wrath"

repositories {
    mavenCentral()
    mavenLocal()
}

// Versions
val kotlinVersion: String by System.getProperties()
val kvisionVersion: String by System.getProperties()

val webDir = file("src/main/web")


kotlin {
    js {
        browser {

            runTask {
                val output = "./src/main/resources/css/main.css"
                exec {
                    println("Executando tailwindcss")
                    executable("./tailwind")
                    args("-o", output)
                }
            }

            runTask {
                outputFileName = "main.bundle.js"
                sourceMaps = false
                devServer = KotlinWebpackConfig.DevServer(
                    open = false,
                    port = 3000,
                    proxy = mutableMapOf(
                        "/kv/*" to "http://localhost:8080",
                        "/kvws/*" to mapOf("target" to "ws://localhost:8080", "ws" to true)
                    ),
                    static = mutableListOf("$buildDir/processedResources/js/main")
                )
            }

            webpackTask {
                outputFileName = "main.bundle.js"
            }

            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
        binaries.executable()
    }

    sourceSets["main"].dependencies {
        implementation("io.kvision:kvision:$kvisionVersion")
        implementation("io.kvision:kvision-i18n:$kvisionVersion")
        implementation("io.kvision:kvision-rest:$kvisionVersion")
        implementation("io.kvision:kvision-state:$kvisionVersion")
        implementation("io.kvision:kvision-toastify:$kvisionVersion")
        implementation("io.kvision:kvision-state-flow:$kvisionVersion")
        implementation("io.kvision:kvision-tom-select:$kvisionVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.6.4")
        implementation(npm("tailwindcss", "2.2.16"))
    }

    sourceSets["test"].dependencies {

    }

    sourceSets["main"].resources.srcDir(webDir)
}