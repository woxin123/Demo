plugins {
    java
    kotlin("jvm") version "1.5.21"
    id("top.mcwebsite.gradle.customPlugin")
}

group = "org.example"
version = "1.0-SNAPSHOT"



team {
    register("xiaoming") {
        age = 18
        isMale = false
    }

    register("xiaohong") {
        age = 18
        isMale = true
    }
}

myExtension {
    foo = "aaaadddd"
    inner {
        str = "test"
    }
}

repositories {
    maven(url = "https://maven.aliyun.com/repository/public")
    google()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.coroutine)
    implementation(libs.rxJava)
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
    implementation("com.bennyhuo.java:portable-android-handler:0.3")
    implementation("com.squareup.retrofit2:retrofit:2.7.0")
    implementation("com.squareup.retrofit2:converter-gson:2.7.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.2")
    implementation("junit:junit:4.12")
    testImplementation("junit:junit:4.12")
}

kotlin {
    target {
        compilations.all {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + "-Xallow-result-return-type"
            }
        }
    }
}

// tasks.jar {
//     manifest {
//         attributes(mapOf("Main-Class" to "top.mcwebsite.kotlin.demo.MainCls"))
//     }
//     from(configurations.runtimeClasspath.get().map {
//         if (it.isDirectory) {
//             it
//         } else {
//             zipTree(it)
//         }
//     })
// }
