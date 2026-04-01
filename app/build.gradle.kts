plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    jacoco
}

android {
    namespace = "com.example.travelhubapp_mobile"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.travelhubapp_mobile"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            // Must be OFF — AGP pre-instrumentation breaks JaCoCo offline mode
            enableUnitTestCoverage = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

jacoco {
    toolVersion = "0.8.12"
}

val compiledClassesDir = layout.buildDirectory.dir(
    "intermediates/built_in_kotlinc/debug/compileDebugKotlin/classes"
)
val jacocoOfflineDir = layout.buildDirectory.dir("jacoco-offline-classes")
val jacocoExecFile = layout.buildDirectory.file("jacoco/testDebugUnitTest.exec")

val instrumentClassesOffline by tasks.registering(JacocoOfflineInstrumentTask::class) {
    dependsOn("compileDebugKotlin")
    inputDirectory.set(compiledClassesDir)
    outputDirectory.set(jacocoOfflineDir)
}

afterEvaluate {
    tasks.named<Test>("testDebugUnitTest") {
        dependsOn(instrumentClassesOffline)
        doFirst {
            // Instrumented classes must come first so Robolectric loads them
            classpath = files(jacocoOfflineDir) + classpath
            // Tell JaCoCo offline runtime where to write the exec file
            systemProperty(
                "jacoco-agent.destfile",
                jacocoExecFile.get().asFile.absolutePath
            )
        }
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/html"))
    }

    classDirectories.setFrom(
        fileTree(compiledClassesDir) {
            exclude(
                "**/R.class", "**/R\$*.class", "**/BuildConfig.*",
                "**/Manifest*.*", "**/ComposableSingletons*.*",
                "**/*\$Lambda\$*.*", "**/*\$inlined\$*.*"
            )
        }
    )
    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(jacocoExecFile)
}

detekt {
    config.setFrom(rootProject.files("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    baseline = file("detekt-baseline.xml")
    source.setFrom(
        "src/main/java",
        "src/test/java",
        "src/androidTest/java"
    )
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)
    implementation(libs.androidx.datastore)
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation("androidx.test:core:1.6.1")
    testImplementation(libs.androidx.compose.ui)
    testImplementation(libs.androidx.compose.ui.test.junit4)
    testImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.androidx.compose.material3)
    testImplementation(libs.androidx.compose.material.icons.extended)
    testImplementation(libs.androidx.navigation.compose)
    testImplementation(libs.androidx.activity.compose)
    // JaCoCo offline runtime — needed so instrumented classes can write exec data
    testImplementation("org.jacoco:org.jacoco.core:0.8.12")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
