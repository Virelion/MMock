plugins {
    kotlin("multiplatform")
}

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
                implementation(kotlin("stdlib-common"))
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
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val nativeMain by creating {
            dependencies {
            }
        }

        val mingwX64Main by getting {
            dependsOn(nativeMain)
        }

        val mingwX86Main by getting {
            dependsOn(nativeMain)
        }
    }
}