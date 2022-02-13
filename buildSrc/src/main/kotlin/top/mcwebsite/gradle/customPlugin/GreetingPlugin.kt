package top.mcwebsite.gradle.customPlugin

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.getByType

open class MyExtension(project: Project) {
    var foo: String = ""
        set(value) {
            println("foo is $value")
            field = value
        }

    val inner = Inner()

    // 创建内部 Extension，名称为 text
    fun inner(action: Action<Inner>) {
        action.execute(inner)
    }

    private var teamNOC: NamedDomainObjectContainer<Student> = project.container(Student::class.java)

    fun team(action: Action<NamedDomainObjectContainer<Student>>) {
        action.execute(teamNOC)
    }

}

class Inner {
    var str: String = ""
}

class Student(var name: String) {
    var age: Int = 0
    var isMale: Boolean = false
}

class GreetingPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val studentContainer: NamedDomainObjectContainer<Student> = project.container(Student::class.java)
        project.extensions.add("team", studentContainer)
        project.project.extensions.create("myExtension", MyExtension::class.java, project)
        project.project.task("hello") {
            doLast {
                println("My Extension foo is ${project.extensions.getByType(MyExtension::class.java).foo}")
                println("inner.str is ${project.extensions.getByType<MyExtension>().inner.str}")

                val team = project.extensions.getByType<NamedDomainObjectContainer<Student>>()
                team.forEach {
                    println("${it.name}/${it.age}/${it.isMale}")
                }
            }
        }
    }
}