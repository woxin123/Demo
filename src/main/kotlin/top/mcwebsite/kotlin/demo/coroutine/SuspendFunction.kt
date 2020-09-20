package top.mcwebsite.kotlin.demo.coroutine

import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

suspend fun suspendFunc01(a: Int) {
    return
}

suspend fun suspendFunc02(a: String, b: String)
    = suspendCoroutine<Int> { continuation ->
    thread {
        continuation.resumeWith(Result.success(5))
    }
}

suspend fun main() {
    val res = suspendFunc02("1", "2")
    println(res)
}

