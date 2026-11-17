package primitive.kmp

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/** Kotlin Multiplatform の JVM（Desktop）target を設定する。 */
class KmpJvmPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            kotlin {
                jvm {
                    compilerOptions {
                        jvmTarget.set(JvmTarget.JVM_21)
                    }
                }
            }
        }
    }
}
