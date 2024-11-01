import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.fabric.loom)
    `maven-publish`
    java
}

group = property("maven_group")!!
version = property("mod_version")!!

val transitiveInclude: Configuration by configurations.creating {
    exclude(group = "com.mojang")
    exclude(group = "org.jetbrains.kotlin")
    exclude(group = "org.jetbrains.kotlinx")
}

dependencies {
    minecraft(libs.minecraft)
    mappings("${libs.yarn.mappings.get()}:v2")
    modImplementation(libs.fabric.loader)

    modImplementation(libs.fabric.kotlin)
    modImplementation(libs.fabric.api)

    implementation(libs.slf4j)

    transitiveInclude.resolvedConfiguration.resolvedArtifacts.forEach {
        include(it.moduleVersion.id.toString())
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(getProperties() + mutableMapOf(
                "version" to project.version,
                "minecraft_version" to libs.versions.minecraft.get(),
                "fabric_loader_version" to libs.versions.fabric.loader.get()
            ))
        }
    }

    jar {
        from("LICENSE")
    }

    remapJar {
        archiveFileName = "jamboree-${version}.jar"
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifact(remapJar) {
                    builtBy(remapJar)
                }
                artifact(kotlinSourcesJar) {
                    builtBy(remapSourcesJar)
                }
            }
        }
    }

    compileKotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
}
