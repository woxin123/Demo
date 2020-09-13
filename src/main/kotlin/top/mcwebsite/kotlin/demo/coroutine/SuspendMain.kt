package top.mcwebsite.kotlin.demo.coroutine

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