package top.mcwebsite.gradle.customPlugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class GreetingPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.project.task("hello") {
            println("Hello from the GreetingPlugin")
        }
    }
}