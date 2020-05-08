buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.github.virelion.mmock:mmock-gradle-plugin:0.0.1-SNAPSHOT")
    }
}

plugins {
    kotlin("multiplatform") version "1.3.72"
}

apply<com.github.virelion.mmock.gradle.MMockPlugin>()

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
}

kotlin {
    jvm()
    js {
        nodejs {
            testTask { }
        }
    }
    mingwX64()
    mingwX86()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":sub-module"))

                implementation(kotlin("stdlib-common"))
                implementation("com.github.virelion.mmock:mmock-runtime:0.0.1-SNAPSHOT")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
                implementation("com.github.virelion.mmock:mmock-runtime-jvm:0.0.1-SNAPSHOT")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.3.5")
                implementation("com.github.virelion.mmock:mmock-runtime-js:0.0.1-SNAPSHOT")
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val nativeMain by creating {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.5")
            }
        }

        val mingwX64Main by getting {
            dependsOn(nativeMain)
            dependencies {
                implementation("com.github.virelion.mmock:mmock-runtime-mingwx64:0.0.1-SNAPSHOT")
            }
        }

        val mingwX86Main by getting {
            dependsOn(nativeMain)
            dependencies {
                implementation("com.github.virelion.mmock:mmock-runtime-mingwx86:0.0.1-SNAPSHOT")
            }
        }
    }
}