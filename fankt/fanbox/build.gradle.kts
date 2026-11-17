plugins {
    id("matsumo.primitive.kmp.common")
    id("matsumo.primitive.android.library")
    id("matsumo.primitive.android.common")
    id("matsumo.primitive.kmp.android")
    id("matsumo.primitive.kmp.ios")
    id("matsumo.primitive.kmp.jvm")
    id("matsumo.primitive.detekt")
    id("matsumo.primitive.maven.publish")
    alias(libs.plugins.zipline)
}

android {
    namespace = "me.matsumo.fankt.fanbox"
}

kotlin {
    sourceSets {
        all {
            languageSettings.optIn("me.matsumo.fankt.fanbox.InternalFanboxApi")
        }

        commonMain.dependencies {
            api(project(":fankt:fanbox-core"))
            implementation(libs.napier)
            implementation(libs.bundles.ktor)
            implementation(libs.zipline.loader)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.ktor.mock)
        }

        // Android and the JVM share the JVM implementation of the Zipline thread.
        val jvmAndAndroidMain by creating {
            dependsOn(commonMain.get())
        }

        androidMain {
            dependsOn(jvmAndAndroidMain)
        }

        named("jvmMain") {
            dependsOn(jvmAndAndroidMain)
        }

        named("jvmTest") {
            dependsOn(jvmAndAndroidMain)
        }

        androidMain.dependencies {
            implementation(libs.ktor.okhttp)
        }

        jvmMain.dependencies {
            api(libs.ktor.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }

        jvmTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

// The JVM tests load the guest bundle that :fankt:fanbox-guest builds.
tasks.named("jvmTest") {
    dependsOn(":fankt:fanbox-guest:compileProductionExecutableKotlinJsZipline")
}
