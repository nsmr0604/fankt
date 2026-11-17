plugins {
    id("matsumo.primitive.kmp.common")
    id("matsumo.primitive.android.library")
    id("matsumo.primitive.android.common")
    id("matsumo.primitive.kmp.android")
    id("matsumo.primitive.kmp.ios")
    id("matsumo.primitive.kmp.jvm")
    id("matsumo.primitive.ktorfit")
    id("matsumo.primitive.room")
    id("matsumo.primitive.detekt")
    id("matsumo.primitive.maven.publish")
}

android {
    namespace = "me.matsumo.fankt.fantia"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.infra.api)
            implementation(libs.bundles.ktor)
            implementation(libs.ktorfit)
            implementation(libs.ksoup)
        }

        jvmMain.dependencies {
            api(libs.ktor.okhttp)
        }

        androidMain.dependencies {
            implementation(libs.androidx.startup)
            implementation(libs.ktor.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.darwin)
        }
    }
}
