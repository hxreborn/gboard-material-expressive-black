plugins {
    base
    alias(libs.plugins.agp.app) apply false
    alias(libs.plugins.ktlint) apply false
}

tasks.register<Exec>("buildLibxposedApi") {
    group = "libxposed"
    description = "Builds libxposed/api and publishes to mavenLocal"
    workingDir = layout.projectDirectory.dir("libxposed/api").asFile
    commandLine(
        "./gradlew",
        ":api:publishApiPublicationToMavenLocal",
        "--no-daemon",
    )
}

tasks.register<Exec>("buildLibxposedService") {
    group = "libxposed"
    description = "Builds libxposed/service and publishes to mavenLocal"
    workingDir = layout.projectDirectory.dir("libxposed/service").asFile
    commandLine(
        "./gradlew",
        ":interface:publishInterfacePublicationToMavenLocal",
        ":service:publishServicePublicationToMavenLocal",
        "--no-daemon",
    )
}

tasks.register("buildLibxposed") {
    group = "libxposed"
    description = "Builds all libxposed modules and publishes to mavenLocal"
    dependsOn("buildLibxposedApi", "buildLibxposedService")
}

tasks.register("assembleDebugRelease") {
    group = "build"
    dependsOn(":app:assembleDebug", ":app:assembleRelease")
}

tasks.register("cleanBuild") {
    group = "build"
    dependsOn("clean", "assembleDebugRelease")
}
