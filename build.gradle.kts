import org.gradle.internal.extensions.stdlib.toDefaultLowerCase
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI
import java.net.URL

plugins {
    id("maven-publish")
    id("net.fabricmc.fabric-loom-remap") version "1.15.+"
    id("ploceus") version "1.15-SNAPSHOT"
}
ploceus {
	setIntermediaryGeneration(2)
}


//noinspection GroovyUnusedAssignment
java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

base.archivesName = project.properties["archives_base_name"] as String
version = project.properties["mod_version"] as String
group = project.properties["maven_group"] as String

repositories {
    mavenLocal()
    maven("https://maven.glass-launcher.net/snapshots/")
    maven("https://maven.glass-launcher.net/releases/")
    maven("https://maven.glass-launcher.net/babric")
    maven("https://maven.minecraftforge.net/")
    maven("https://jitpack.io/")
    maven("https://maven.fildand.cz/releases") // NyaRepo
    mavenCentral()
    exclusiveContent {
        forRepository {
            maven("https://api.modrinth.com/maven")
        }
        filter {
            includeGroup("maven.modrinth")
        }
    }
    maven("https://maven.ornithemc.net/releases")
    maven("https://mvn.devos.one/releases")
    maven("https://matthewperiut.github.io/repository")
}

dependencies {
    minecraft("com.mojang:minecraft:b1.7.3")
    mappings(ploceus.mappings("net.glasslauncher:biny-ornithe:b1.7.3+build.${project.properties["biny_mappings"]}:mergedv2"))

    "clientExceptions"(ploceus.raven(project.properties["client_raven_build"] as String, "client"))
    "serverExceptions"(ploceus.raven(project.properties["server_raven_build"] as String, "server"))
    "clientSignatures"(ploceus.sparrow(project.properties["client_sparrow_build"] as String, "client"))
    "serverSignatures"(ploceus.sparrow(project.properties["server_sparrow_build"] as String, "server"))
    "clientNests"("net.glasslauncher:biny-nests:b1.7.3-client+build.2")
    "serverNests"("net.glasslauncher:biny-nests:b1.7.3-server+build.2")
    modImplementation("net.fabricmc:fabric-loader:${project.properties["loader_version"]}")

    implementation("org.apache.logging.log4j:log4j-core:2.17.2")

    implementation("org.slf4j:slf4j-api:1.8.0-beta4")
    implementation("org.apache.logging.log4j:log4j-slf4j18-impl:2.17.1")

    // convenience stuff
    // adds some useful annotations for data classes. does not add any dependencies
    compileOnly("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    // adds some useful annotations for miscellaneous uses. does not add any dependencies, though people without the lib will be missing some useful context hints.
    implementation("org.jetbrains:annotations:23.0.0")
    implementation("com.google.guava:guava:33.2.1-jre")

    // StAPI itself.
    // transitiveImplementation tells babric loom that you want this dependency to be pulled into other mod's development workspaces. Best used ONLY for required dependencies.
    modImplementation("net.modificationstation:StationAPI:${project.properties["stationapi_version"]}")

    modImplementation("com.periut:retrocommands:${project.properties["retrocommands_version"]}")
    modImplementation("com.periut:accessory-api:${project.properties["accessory_version"]}")
    modImplementation("paulevs.bhcreative:BHCreative:${project.properties["bhcreative_version"]}")
    
    // Extra mods.
    // https://github.com/calmilamsy/glass-config-api
    modImplementation("net.glasslauncher.mods:GlassConfigAPI:${project.properties["gcapi_version"]}")
    // https://github.com/calmilamsy/modmenu
    // TODO: babric2ornithe - verify this dependency is available for ornithe
    // modImplementation("net.glasslauncher.mods:ModMenu:${project.properties["modmenu_version"]}")
    // https://github.com/Glass-Series/Always-More-Items
    // TODO: babric2ornithe - verify this dependency is available for ornithe
    // modImplementation("net.glasslauncher.mods:AlwaysMoreItems:${project.properties["alwaysmoreitems_version"]}")
    // https://github.com/DanyGames2014/spawneggs
    // TODO: babric2ornithe - verify this dependency is available for ornithe
    // modImplementation("net.danygames2014:spawneggs:${project.properties["spawneggs_version"]}")

    // Runtime
    // TODO: babric2ornithe - verify this dependency is available for ornithe
    // modRuntimeOnly("maven.modrinth:thirdpersonfix-babric:1.1.0")
    // TODO: babric2ornithe - verify this dependency is available for ornithe
    // modRuntimeOnly("maven.modrinth:retroauth:1.1.1")
    // TODO: babric2ornithe - verify this dependency is available for ornithe
    // modRuntimeOnly ("maven.modrinth:fast-stapi-intro:2.0.0")

    modImplementation("com.periut:starac:${project.properties["starac_version"]}")
}

// Exclude Legacy Fabric LWJGL 2 wrapper (starac provides LWJGL 3)
configurations.configureEach {
    exclude(group = "org.lwjgl.lwjgl")
}

configurations.all {
    exclude("babric")
}

tasks.withType<ProcessResources> {
    inputs.property("version", project.properties["version"])

    filesMatching("fabric.mod.json") {
        expand(mapOf("version" to project.properties["version"]))
    }
}

// ensure that the encoding is set to UTF-8, no matter what the system default is
// this fixes some edge cases with special characters not displaying correctly
// see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

java {
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

tasks.withType<Jar> {
    from("LICENSE") {
        rename { "${it}_${project.properties["archives_base_name"]}" }
    }
}

publishing {
    repositories {
        mavenLocal()
        if (project.hasProperty("my_maven_username")) {
            maven {
                url = URI("https://maven.example.com")
                credentials {
                    username = "${project.properties["my_maven_username"]}"
                    password = "${project.properties["my_maven_password"]}"
                }
            }
        }
    }

    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = project.properties["archives_base_name"] as String
            from(components["java"])
        }
    }
}
