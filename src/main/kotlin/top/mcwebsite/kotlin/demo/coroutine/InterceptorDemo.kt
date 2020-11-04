package top.mcwebsite.kotlin.demo.coroutine

import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine

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

fun main() {
    suspend {
        suspendFunc02("Hello", "Hello1")
        suspendFunc02("Hello", "Hello2")
    }.startCoroutine(object : Continuation<Int> {
        override val context: CoroutineContext = LogInterceptor()

        override fun resumeWith(result: Result<Int>) {
            result.getOrThrow()
        }

    })
}