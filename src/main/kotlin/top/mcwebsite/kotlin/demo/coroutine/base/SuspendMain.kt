package top.mcwebsite.kotlin.demo.coroutine

import top.mcwebsite.kotlin.demo.coroutine.base.runSuspend

//suspend fun main() {
//
//}

fun main() {
    runSuspend() {
        suspendMain()
    }
}



fun suspendMain() {
    println("Hello World!")
}