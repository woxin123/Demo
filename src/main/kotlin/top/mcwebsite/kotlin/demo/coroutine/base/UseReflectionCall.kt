package top.mcwebsite.kotlin.demo.coroutine

import top.mcwebsite.kotlin.demo.coroutine.base.notSuspend
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


fun main() {
    val ref = ::notSuspend
    val result = ref.call(object : Continuation<Int> {
        override val context: CoroutineContext = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            println("resumeWith: ${result.getOrNull()}")
        }

    })
    println(result)
}