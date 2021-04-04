package top.mcwebsite.kotlin.demo.coroutine.base

import kotlin.coroutines.*

fun main() {
    suspend {
        notSuspend()
        suspendFunc02("Hello", "Coroutine")
    }.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext
            get() = LogInterceptor()

        override fun resumeWith(result: Result<Int>) {
            result.getOrThrow()
        }
    })
}


class LogInterceptor : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
        LogContinuation(continuation)

}

class LogContinuation<T>(private val continuation: Continuation<T>)
    : Continuation<T> by continuation {

    override fun resumeWith(result: Result<T>) {
        println("before resumeWith: $result")
        continuation.resumeWith(result)
        println("after resumeWith.")
    }
}
