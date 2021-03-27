package top.mcwebsite.kotlin.demo.base

class Test {
    companion object {

        var a = 0

        fun plus() {
            synchronized(Test::class.java) {
                a++
            }
        }


    }
}

fun main() {
    for (i in 0..500) {
        Test.plus()
    }
    println(Test.a)
}