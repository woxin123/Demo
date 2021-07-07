@file:JvmName("MainCls")
package top.mcwebsite.kotlin.demo

import top.mcwebsite.java.demo.JavaTest

suspend fun main() {
    val test = JavaTest().test()
    val path = test.absolutePath

    println(test.absolutePath)
}

