package top.mcwebsite.kotlin.demo.base

class Person {


    constructor(name: String, gender: Boolean) {
        println("constructor")
        test()
    }

    init {
        println("Person init1")
        test()
    }

    private var gender: Any = Unit

    init {
        println("Person init2")
        test()
    }

    fun test() {
        println(gender)
    }

}

fun main() {
    Person("张三", false)
}