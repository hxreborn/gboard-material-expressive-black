plugins {
    alias(libs.plugins.agp.app)
    alias(libs.plugins.kotlin)
}

android {
    namespace = "eu.hxreborn.gboardmaterialexpressiveblack"
    compileSdk = 35

    defaultConfig {
        applicationId = "eu.hxreborn.gboardmaterialexpressiveblack"
        minSdk = 31
        targetSdk = 35

        versionCode = 100
        versionName = "1.0.0"
    }

    signingConfigs {
        create("release") {
            fun secret(name: String): String? =
                providers.gradleProperty(name)
                    .orElse(providers.environmentVariable(name))
                    .orNull

            val storeFilePath = secret("RELEASE_STORE_FILE")
            val storePassword = secret("RELEASE_STORE_PASSWORD")
            val keyAlias = secret("RELEASE_KEY_ALIAS")
            val keyPassword = secret("RELEASE_KEY_PASSWORD")
            val storeType = secret("RELEASE_STORE_TYPE") ?: "PKCS12"

            if (!storeFilePath.isNullOrBlank()) {
                storeFile = file(storeFilePath)
                this.storePassword = storePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword
                this.storeType = storeType

                enableV1Signing = false
                enableV2Signing = true
            } else {
                logger.warn("RELEASE_STORE_FILE not found. Release signing is disabled.")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }

    buildFeatures {
        viewBinding = false
        buildConfig = false
    }

    kotlin {
        jvmToolchain(21)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    packaging {
        resources {
            merges += "META-INF/xposed/*"
            excludes += setOf(
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/*.kotlin_module",
                "META-INF/INDEX.LIST"
            )
        }
    }

    lint {
        abortOnError = true
        disable.add("OldTargetApi")
    }
}

dependencies {
    implementation(libs.libxposed.service)
    compileOnly(libs.libxposed.api)
}

tasks.register("validateVersionSync") {
    doLast {
        val gradleVersionCode = android.defaultConfig.versionCode
        val gradleVersionName = android.defaultConfig.versionName

        val modulePropFile = file("src/main/resources/META-INF/xposed/module.prop")
        val propLines = modulePropFile.readLines()

        val propVersionCode = propLines
            .find { it.startsWith("versionCode=") }
            ?.substringAfter("=")
            ?.toIntOrNull()

        val propVersion = propLines
            .find { it.startsWith("version=") }
            ?.substringAfter("=")

        if (gradleVersionCode != propVersionCode) {
            throw GradleException(
                "Version mismatch: build.gradle.kts versionCode ($gradleVersionCode) != " +
                "module.prop versionCode ($propVersionCode)"
            )
        }

        if (gradleVersionName != propVersion) {
            throw GradleException(
                "Version mismatch: build.gradle.kts versionName ($gradleVersionName) != " +
                "module.prop version ($propVersion)"
            )
        }

        logger.lifecycle("Version sync validated: $gradleVersionName ($gradleVersionCode)")
    }
}

tasks.named("preBuild") {
    dependsOn("validateVersionSync")
}
