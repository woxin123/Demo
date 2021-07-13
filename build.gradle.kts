plugins {
    java
    kotlin("jvm") version "1.4.30"
}

group = "org.example"
version = "1.0-SNAPSHOT"



repositories {
    maven(url = "https://maven.aliyun.com/repository/public")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlin.coroutine)
    implementation(libs.rxJava)
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
    implementation("com.bennyhuo.java:portable-android-handler:0.3")
    implementation("com.squareup.retrofit2:retrofit:2.7.0")
    implementation("com.squareup.retrofit2:converter-gson:2.7.0")
    implementation("com.squareup.okhttp3:logging-interceptor:3.14.2")
    implementation("junit:junit:4.12")
    testCompile("junit:junit:4.12")
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
