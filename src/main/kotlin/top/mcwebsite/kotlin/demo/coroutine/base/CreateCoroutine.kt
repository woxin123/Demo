package top.mcwebsite.kotlin.demo.coroutine.base

import kotlin.coroutines.*

/**
 * 标准库中提供了一个 createCoroutine 函数，我们可以通过这个函数来创建协程，不过这个协程并不会立即执行。
 * 首先我们来看看它的声明：
 * public fun <T> (suspend () -> T).createCoroutine(
 *      completion: Continuation<T>
 * ): Continuation<Unit>
 * 其中 suspend() -> T 是 createCoroutine 函数的 Receiver。
 * - Receiver 是一个被 suspend 挂起的函数，这也是协程的执行体，可以称为协程体
 * - 参数 completion 会在协程执行完成后调用，实际上就是协程的完成回调
 * - 返回值是一个 Continuation 对象，由于协程仅仅被创建出来，因此需要通过这个值在之后出发协程的启动。
 */
fun main() {
    val continuation = suspend {
        println("协程执行中...")
        "协程的返回值"
    }.createCoroutine(object : Continuation<String> {
        override fun resumeWith(result: Result<String>) {
            println("协程执行结束: $result")
        }

        override val context: CoroutineContext = EmptyCoroutineContext

    })

    continuation.resume(Unit)
}