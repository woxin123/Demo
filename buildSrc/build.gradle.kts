plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

gradlePlugin {
    plugins {
        create("simplePlugin") {
            id = "top.mcwebsite.gradle.customPlugin"
            implementationClass = "top.mcwebsite.gradle.customPlugin.GreetingPlugin"
        }
    }
}
